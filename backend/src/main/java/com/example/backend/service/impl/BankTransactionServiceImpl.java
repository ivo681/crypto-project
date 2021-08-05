package com.example.backend.service.impl;

import com.example.backend.model.BankAccount;
import com.example.backend.model.BankTransaction;
import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.enums.TransactionStatusEnum;
import com.example.backend.repository.BankAccountRepository;
import com.example.backend.repository.BankTransactionRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.service.BankTransactionService;
import com.example.backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;
    private final OrderService orderService;
    private final BankAccountRepository bankAccountRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public BankTransactionServiceImpl(BankTransactionRepository bankTransactionRepository, OrderService orderService, BankAccountRepository bankAccountRepository, OrderRepository orderRepository, ModelMapper modelMapper, Random random) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.orderService = orderService;
        this.bankAccountRepository = bankAccountRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }


//    @Override
//    public List<OrderTransactionViewModel> getAllTransactionsForOrders() {
//        List<OrderTransactionViewModel> transactionViewModels = new ArrayList<>();
//
//        List<Order> orders = this.orderRepository.findAll();
//        for (Order order : orders) {
//            for (BankTransaction bankTransaction : order.getBankTransactions()) {
//                OrderTransactionViewModel orderTransactionViewModel = this.modelMapper.map(order, OrderTransactionViewModel.class);
//                orderTransactionViewModel.setNumber(bankTransaction.getNumber());
//                orderTransactionViewModel.setTransactionStatus(bankTransaction.getTransactionStatus().name());
//                orderTransactionViewModel.setTotalSum(bankTransaction.getAmount());
//                orderTransactionViewModel.setOrderNumber(order.getNumber());
//                orderTransactionViewModel.setUserName(order.getUser().getEmail());
//                transactionViewModels.add(orderTransactionViewModel);
//            }
//        }
//        return transactionViewModels;
//    }

    @Override
    public Long createSuccessfulTransaction(Long orderId, String number, OrderTypeEnum type) {
        BigDecimal orderTotal = this.orderService.getOrderTotal(orderId);
        BankAccount bankAccount = this.bankAccountRepository.findByNumber(number).get();
        if (type.equals(OrderTypeEnum.PURCHASE)){
            bankAccount.setBlockedAmount(bankAccount.getBlockedAmount().add(orderTotal));
            bankAccount = this.bankAccountRepository.save(bankAccount);
        }
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setNumber(generateNumber());
        bankTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFULL);
        bankTransaction.setAmount(orderTotal);
        bankTransaction.setDate(LocalDate.now());
        bankTransaction = this.bankTransactionRepository.save(bankTransaction);
        this.orderService.changeStatusAndOwnership(orderId, bankTransaction.getId(), OrderStatusEnum.COMPLETE);
        return bankTransaction.getNumber();
    }

    @Override
    public Long createUnsuccessfulTransaction(Long orderId, String number) {
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(this.bankAccountRepository.findByNumber(number).get());
        bankTransaction.setTransactionStatus(TransactionStatusEnum.DECLINED);
        bankTransaction.setNumber(generateNumber());
        bankTransaction.setAmount(this.orderService.getOrderTotal(orderId));
        bankTransaction.setDate(LocalDate.now());
        bankTransaction = this.bankTransactionRepository.save(bankTransaction);
        this.orderService.changeStatusAndOwnership(orderId, bankTransaction.getId(), OrderStatusEnum.DECLINED);
        return bankTransaction.getNumber();
    }

    @Override
    public boolean isNumberTaken(Long number) {
        return this.bankTransactionRepository.findByNumber(number).isPresent();
    }

    private Long generateNumber() {
        long number = (10000000 + this.random.nextInt(9000000));
        while (isNumberTaken(number)) {
            number = (10000000 + this.random.nextInt(9000000));
        }
        return number;
    }
}
