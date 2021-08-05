package com.example.backend.service;

import com.example.backend.model.enums.OrderTypeEnum;

public interface BankTransactionService {

//    List<OrderTransactionViewModel> getAllTransactionsForOrders();

    Long createSuccessfulTransaction(Long orderId, String number, OrderTypeEnum type);

    Long createUnsuccessfulTransaction(Long orderId, String number);

    boolean isNumberTaken(Long number);
}
