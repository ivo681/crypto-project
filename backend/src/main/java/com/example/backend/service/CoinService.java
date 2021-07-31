package com.example.backend.service;

import java.io.IOException;

public interface CoinService {

    void seedCoins() throws IOException;

    void updateCoinPrices();

    void updateCoinAveragePrices();
}
