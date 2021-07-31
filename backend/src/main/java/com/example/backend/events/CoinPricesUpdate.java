package com.example.backend.events;

import com.example.backend.service.CoinService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CoinPricesUpdate {
    private CoinService coinService;

    public CoinPricesUpdate(CoinService coinService) {
        this.coinService = coinService;
    }

    @Transactional
    @Scheduled(cron = "0 0 */1 * * *")
    public void updateCoinPrices(){
        this.coinService.updateCoinPrices();
    }

    @Transactional
    @Scheduled(cron = "0 58 23 * * *")
    public void updateAveragePrices(){
        this.coinService.updateCoinAveragePrices();
    }
}
