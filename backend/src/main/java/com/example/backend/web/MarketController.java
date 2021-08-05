package com.example.backend.web;

import com.example.backend.exceptions.CoinNotFoundException;
import com.example.backend.model.binding.OrderBindingModel;
import com.example.backend.model.binding.PaymentBindingModel;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.service.CoinServiceModel;
import com.example.backend.model.service.OrderServiceModel;
import com.example.backend.service.BankAccountService;
import com.example.backend.service.CoinService;
import com.example.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {
    private final CoinService coinService;
    private final OrderService orderService;
    private final BankAccountService bankAccountService;

    public MarketController(CoinService coinService, OrderService orderService, BankAccountService bankAccountService) {
        this.coinService = coinService;
        this.orderService = orderService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public List<CoinServiceModel> getAllAvailableCoins(){
        return this.coinService.getAllAvailableCoins();
    }

    @GetMapping("/buy/{name}")
    public ResponseEntity<Object> getAvailableCoinDetailsByName(@PathVariable String name) throws CoinNotFoundException {
        try {
        CoinServiceModel serviceModel = this.coinService.getAvailableCoinDetailsByName(name);
            return new ResponseEntity(serviceModel, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Object> placeOrder(
            @RequestBody OrderBindingModel orderBindingModel, Principal principal){
        try {
            Long orderNumber = this.orderService.createOrder(orderBindingModel, principal.getName(), OrderTypeEnum.PURCHASE);
            return new ResponseEntity<>(
                    orderNumber, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<Object> getOrderServiceModel(
            @PathVariable Long orderNumber, Principal principal
    ){
        try {
            OrderServiceModel orderServiceModel =
                    this.orderService.getOrderViewModel(
                    orderNumber, principal.getName());
            return new ResponseEntity<>(orderServiceModel, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/checkout/{orderNumber}")
    public ResponseEntity<Object> makePurchase(
            @PathVariable Long orderNumber, @RequestBody PaymentBindingModel paymentBindingModel
    ){
        if (paymentBindingModel != null && !this.orderService.isOrderCompleted(orderNumber)){
            String[] tokens = paymentBindingModel.getValidTo().split("/");
            int year = Integer.parseInt("20" + tokens[1]);
            int month = Integer.parseInt(tokens[0]);
            LocalDate validTo = LocalDate.of(year, month, 1);
            if (this.bankAccountService.checkIfBankIsValid(
                    paymentBindingModel.getCardNumber(), paymentBindingModel.getCvv(),
                    paymentBindingModel.getFullName(), validTo
            )){
                if (this.bankAccountService.hasEnoughBalance(orderNumber,
                        paymentBindingModel.getCardNumber())){
                    Long successfulTransaction = this.bankAccountService.createSuccessfulTransaction(
                            orderNumber, paymentBindingModel.getCardNumber(), OrderTypeEnum.PURCHASE
                    );
                    return new ResponseEntity<>(successfulTransaction, HttpStatus.OK);
                } else {
                    this.bankAccountService.createUnsuccessfulTransaction(
                            orderNumber, paymentBindingModel.getCardNumber()
                    );
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
