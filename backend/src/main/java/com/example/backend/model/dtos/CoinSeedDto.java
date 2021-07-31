package com.example.backend.model.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class CoinSeedDto {
    @Expose
    private String name;
    @Expose
    private Long quantity;
    @Expose
    private BigDecimal initialPrice;
    @Expose
    private BigDecimal yesterdaysAvgPrice;
    @Expose
    private List<BigDecimal> dailyPrices;
    @Expose
    private BigDecimal previousPrice;
    @Expose
    private BigDecimal currentPrice;

    public CoinSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getYesterdaysAvgPrice() {
        return yesterdaysAvgPrice;
    }

    public void setYesterdaysAvgPrice(BigDecimal yesterdaysAvgPrice) {
        this.yesterdaysAvgPrice = yesterdaysAvgPrice;
    }

    public List<BigDecimal> getDailyPrices() {
        return dailyPrices;
    }

    public void setDailyPrices(List<BigDecimal> dailyPrices) {
        this.dailyPrices = dailyPrices;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
