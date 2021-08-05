package com.example.backend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface BankAccountService {
    void seedAccounts() throws IOException;

    boolean checkIfBankIsValid(String number, int cvv, String fullName, LocalDate validTo);

    boolean hasEnoughBalance(Long orderId, String number);

    Long createUnsuccessfulTransaction(Long orderId, String number);

    Long createSuccessfulTransaction(Long orderId, String number);

    void removeBlockedAmountsAndTransferToShop();

    boolean canPayoutUsers(BigDecimal totalSum, String cardNumber);

    void payoutUserAndBlockAmount(String cardNumber, BigDecimal totalSum);
}
