package com.example.backend.service;

public interface BankTransactionService {

//    List<OrderTransactionViewModel> getAllTransactionsForOrders();

    Long createSuccessfulTransaction(Long orderId, String number);

    Long createUnsuccessfulTransaction(Long orderId, String number);

    boolean isNumberTaken(Long number);
}
