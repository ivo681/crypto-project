package com.example.backend.service.impl;

import com.example.backend.model.BankAccount;
import com.example.backend.model.dtos.BankAccountSeedDto;
import com.example.backend.repository.BankAccountRepository;
import com.example.backend.service.BankAccountService;
import com.example.backend.service.BankTransactionService;
import com.example.backend.service.OrderService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final static String BANK_ACCOUNTS_PATH = "D:\\IntelliJ Projects\\angular-project\\backend\\src\\main\\resources\\static\\json\\bankAccounts.json";
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionService bankTransactionService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankTransactionService bankTransactionService, OrderService orderService, ModelMapper modelMapper, Gson gson) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionService = bankTransactionService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedAccounts() throws IOException {
        if (this.bankAccountRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(BANK_ACCOUNTS_PATH)));
            BankAccountSeedDto[] bankAccountSeedDtos = this.gson.fromJson(content, BankAccountSeedDto[].class);
            for (BankAccountSeedDto bankAccountSeedDto : bankAccountSeedDtos) {
                BankAccount bankAccount = this.modelMapper.map(bankAccountSeedDto, BankAccount.class);
                bankAccount.setValidTo(bankAccount.getValidFrom().plusYears(3));
                this.bankAccountRepository.save(bankAccount);
            }

        }
    }

    @Override
    public boolean checkIfBankIsValid(String number, int cvv, String fullName, LocalDate validTo) {
        LocalDate lower = LocalDate.of(validTo.getYear(), validTo.getMonth(), 1);
        LocalDate upper = LocalDate.of(validTo.getYear(), validTo.getMonth().plus(1), 1);
        return this.bankAccountRepository.findAccountByDetails(number, cvv, fullName, lower, upper).isPresent();
    }

    @Override
    public boolean hasEnoughBalance(Long orderId, String number) {
        BigDecimal orderTotal = this.orderService.getOrderTotal(orderId);
        return this.bankAccountRepository.checkIfAccountHasEnoughBalance(number, orderTotal).isPresent();
    }

    //This is only for display, should be in separate Banking application
    @Override
    public void removeBlockedAmountsAndTransferToShop() {
        List<BankAccount> bankAccounts = this.bankAccountRepository.findAll();
        BigDecimal dailyProfits = BigDecimal.ZERO;
        for (BankAccount bankAccount : bankAccounts) {
            BigDecimal blockedAmount = bankAccount.getBlockedAmount();
            bankAccount.setBalance(bankAccount.getBalance().subtract(blockedAmount));
            bankAccount.setBlockedAmount(BigDecimal.ZERO);
            dailyProfits = dailyProfits.add(blockedAmount);
            this.bankAccountRepository.save(bankAccount);
        }
        BankAccount shopAccount = this.bankAccountRepository.findByNumber("1111222233334444").get();
        shopAccount.setBalance(shopAccount.getBalance().add(dailyProfits));
        this.bankAccountRepository.save(shopAccount);
    }

    @Override
    public boolean canPayoutUsers(BigDecimal totalSum, String cardNumber) {
        return this.bankAccountRepository.checkIfAccountHasEnoughBalance(cardNumber, totalSum).isPresent();
    }

    @Override
    public void payoutUserAndBlockAmount(String cardNumber, BigDecimal totalSum) {
        BankAccount bankAccount = this.bankAccountRepository.findByNumber(cardNumber).get();
        bankAccount.setBalance(bankAccount.getBalance().add(totalSum));
        this.bankAccountRepository.save(bankAccount);
        BankAccount ownerAccount = this.bankAccountRepository.findByNumber("1111222233334444").get();
        ownerAccount.setBalance(ownerAccount.getBalance().subtract(totalSum));
        this.bankAccountRepository.save(ownerAccount);
    }

    @Override
    public Long createUnsuccessfulTransaction(Long orderId, String number) {
        return this.bankTransactionService.createUnsuccessfulTransaction(orderId, number);
    }

    @Override
    public Long createSuccessfulTransaction(Long orderId, String number) {
        return this.bankTransactionService.createSuccessfulTransaction(orderId, number);
    }
}
