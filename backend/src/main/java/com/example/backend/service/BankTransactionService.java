package com.example.backend.service;

import java.util.List;

public interface BankTransactionService {

//    List<OrderTransactionViewModel> getAllTransactionsForOrders();

    String createSuccessfulTransaction(String orderId, String number);

    String createUnsuccessfulTransaction(String orderId, String number);

    boolean isNumberTaken(Long number);
}
