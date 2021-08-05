package com.example.backend.service;

import com.example.backend.exceptions.CoinNotFoundException;
import com.example.backend.model.service.CoinServiceModel;

import java.io.IOException;
import java.util.List;

public interface CoinService {

    void seedCoins() throws IOException;

    void updateCoinPrices();

    void updateCoinAveragePrices();

    List<CoinServiceModel> getAllAvailableCoins();

    CoinServiceModel getAvailableCoinDetailsByName(String name) throws CoinNotFoundException;

}
