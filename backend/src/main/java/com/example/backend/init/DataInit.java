package com.example.backend.init;


import com.example.backend.service.BankAccountService;
import com.example.backend.service.CoinService;
import com.example.backend.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final BankAccountService bankAccountService;
    private final CoinService coinService;

    public DataInit(UserRoleService userRoleService, BankAccountService bankAccountService, CoinService coinService) {
        this.userRoleService = userRoleService;
        this.bankAccountService = bankAccountService;
        this.coinService = coinService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedRoles();
        this.bankAccountService.seedAccounts();
        this.coinService.seedCoins();
    }
}
