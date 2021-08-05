package com.example.backend.service.impl;

import com.example.backend.exceptions.CoinNotFoundException;
import com.example.backend.model.Coin;
import com.example.backend.model.dtos.CoinSeedDto;
import com.example.backend.model.service.CoinServiceModel;
import com.example.backend.repository.CoinRepository;
import com.example.backend.service.CoinService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoinServiceImpl implements CoinService {
    private final static String COINS_PATH = "D:\\IntelliJ Projects\\angular-project\\backend\\src\\main\\resources\\static\\json\\coins.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final Random random;
    private final CoinRepository coinRepository;

    public CoinServiceImpl(Gson gson, ModelMapper modelMapper, Random random, CoinRepository coinRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.random = random;
        this.coinRepository = coinRepository;
    }

    @Override
    public void seedCoins() throws IOException {
        if (this.coinRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(COINS_PATH)));
            CoinSeedDto[] coinSeedDtos = this.gson.fromJson(content, CoinSeedDto[].class);
            for (CoinSeedDto coinSeedDto : coinSeedDtos) {
                Coin coin = this.modelMapper.map(coinSeedDto, Coin.class);
                this.coinRepository.save(coin);
            }
        }
    }

    @Override
    public void updateCoinPrices() {
        List<Coin> coins = this.coinRepository.findAll();
        for (Coin coin : coins) {
            List<BigDecimal> dailyPrices = coin.getDailyPrices();
            dailyPrices.add(coin.getCurrentPrice());
            coin.setDailyPrices(dailyPrices);
            coin.setPreviousPrice(coin.getCurrentPrice());
            double randomValue = 0.1 + (2 - 0.1) * random.nextDouble();
            BigDecimal newCurrentPrice = coin.getCurrentPrice().multiply(BigDecimal.valueOf(randomValue));
            if (newCurrentPrice.doubleValue() > 0.1){
                coin.setCurrentPrice(newCurrentPrice);
            }
            this.coinRepository.save(coin);
        }
    }

    @Override
    public void updateCoinAveragePrices() {
        List<Coin> coins = this.coinRepository.findAll();
        for (Coin coin : coins) {
            List<BigDecimal> dailyPrices = coin.getDailyPrices();
            if (dailyPrices.size() > 0){
                BigDecimal sum = BigDecimal.ZERO;
                for (BigDecimal currentValue : dailyPrices) {
                    sum = sum.add(currentValue);
                }
                coin.setYesterdaysAvgPrice(sum.divide(BigDecimal.valueOf(dailyPrices.size()), 2, RoundingMode.HALF_EVEN));
                coin.setDailyPrices(new ArrayList<>());
                this.coinRepository.save(coin);
            }
        }
    }

    @Override
    public List<CoinServiceModel> getAllAvailableCoins() {
        return this.coinRepository.getAllAvailableCoins().stream().map(
                c -> this.modelMapper.map(c, CoinServiceModel.class)
        ).collect(Collectors.toList());
    }

    @Override
    public CoinServiceModel getAvailableCoinDetailsByName(String name) throws CoinNotFoundException {
        String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1);
        Optional<Coin> coin = this.coinRepository.
                getAvailableCoinDetailsByName(capitalName);
        if (coin.isPresent()){
            return this.modelMapper.map(coin.get(), CoinServiceModel.class);
        }
        throw new CoinNotFoundException();
    }

//    public BigDecimal getAverage(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
//        BigDecimal sum = bigDecimals.stream()
//                .map(Objects::requireNonNull)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        return sum.divide(new BigDecimal(bigDecimals.size()), roundingMode);
//    }
}
