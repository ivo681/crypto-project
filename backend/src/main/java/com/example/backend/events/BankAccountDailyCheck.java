package com.example.backend.events;

import com.example.backend.service.BankAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BankAccountDailyCheck {
    private final BankAccountService bankAccountService;

    public BankAccountDailyCheck(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Transactional
    @Scheduled(cron = "0 55 23 * * *")
    public void subtractBlockedAmounts(){
        this.bankAccountService.removeBlockedAmountsAndTransferToShop();
    }
}
