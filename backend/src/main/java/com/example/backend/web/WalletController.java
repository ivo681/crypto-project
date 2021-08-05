package com.example.backend.web;

import com.example.backend.model.binding.OrderBindingModel;
import com.example.backend.model.binding.PaymentBindingModel;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.service.CoinServiceModel;
import com.example.backend.model.service.SellCoinServiceModel;
import com.example.backend.service.BankAccountService;
import com.example.backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final OrderService orderService;
    private final BankAccountService bankAccountService;
    private final ModelMapper modelMapper;

    public WalletController(OrderService orderService, BankAccountService bankAccountService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.bankAccountService = bankAccountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CoinServiceModel> getAllOwnedCoins(Principal principal){
        return this.orderService.getOwnedUserCoins(principal.getName());
    }

    @GetMapping("/coin-details/{coiName}")
    public ResponseEntity<Object> getOrderServiceModel(
            @PathVariable String coiName, Principal principal
    ){
        try {
            CoinServiceModel coinServiceModel =
                    this.orderService.getOwnedCoinDetailsByNameAndEmail(
                            coiName, principal.getName());
            OrderBindingModel orderBindingModel = this.modelMapper.map(coinServiceModel, OrderBindingModel.class);
            orderBindingModel.setUserEmail(principal.getName());
            orderBindingModel.setPrice(coinServiceModel.getCurrentPrice());
            Long orderNumber = this.orderService.createOrder(orderBindingModel,
                    principal.getName(), OrderTypeEnum.SALE);
            SellCoinServiceModel sellCoinServiceModel = new SellCoinServiceModel();
            sellCoinServiceModel.setCoinModel(coinServiceModel);
            sellCoinServiceModel.setOrderNumber(orderNumber);
            return new ResponseEntity<>(sellCoinServiceModel, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sell/{orderNumber}")
    public ResponseEntity<Object> makeSell(
            @PathVariable Long orderNumber, @RequestBody PaymentBindingModel paymentBindingModel,
    Principal principal){
        if (paymentBindingModel != null && !this.orderService.isOrderCompleted(orderNumber)){
            BigDecimal totalSum = this.orderService.getOrderTotal(orderNumber);
            String[] tokens = paymentBindingModel.getValidTo().split("/");
            int year = Integer.parseInt("20" + tokens[1]);
            int month = Integer.parseInt(tokens[0]);
            LocalDate validTo = LocalDate.of(year, month, 1);
            if (this.bankAccountService.checkIfBankIsValid(
                    paymentBindingModel.getCardNumber(), paymentBindingModel.getCvv(),
                    paymentBindingModel.getFullName(), validTo
            )){
                if (this.bankAccountService.canPayoutUsers(totalSum,
                        "1111222233334444")){
                    this.bankAccountService.payoutUserAndBlockAmount(paymentBindingModel.getCardNumber(), totalSum);
                    Long successfulTransaction = this.bankAccountService.createSuccessfulTransaction(orderNumber, paymentBindingModel.getCardNumber());
                    return new ResponseEntity<>(successfulTransaction, HttpStatus.OK);
                } else {
                    this.bankAccountService.createUnsuccessfulTransaction(orderNumber, paymentBindingModel.getCardNumber());
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
