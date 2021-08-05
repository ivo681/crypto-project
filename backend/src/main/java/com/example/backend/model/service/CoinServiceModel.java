package com.example.backend.model.service;

import java.math.BigDecimal;
import java.util.List;

public class CoinServiceModel {
    private String name;
    private Long quantity;
    private BigDecimal initialPrice;
    private BigDecimal yesterdaysAvgPrice;
    private BigDecimal previousPrice;
    private BigDecimal currentPrice;

    public CoinServiceModel() {
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
