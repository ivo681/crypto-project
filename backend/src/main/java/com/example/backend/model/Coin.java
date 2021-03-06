package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "coins")
public class Coin extends BaseEntity{
    private String name;
    private Long quantity;
    private BigDecimal initialPrice;
    private BigDecimal yesterdaysAvgPrice;
    private List<BigDecimal> dailyPrices;
    private BigDecimal previousPrice;
    private BigDecimal currentPrice;

    public Coin() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "quantity", nullable = false)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "current_price", nullable = false)
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Column(name = "initial_price", nullable = false)
    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    @Column(name = "yesterday_avg_price", nullable = false)
    public BigDecimal getYesterdaysAvgPrice() {
        return yesterdaysAvgPrice;
    }

    public void setYesterdaysAvgPrice(BigDecimal yesterdaysAvgPrice) {
        this.yesterdaysAvgPrice = yesterdaysAvgPrice;
    }

    @ElementCollection
    public List<BigDecimal> getDailyPrices() {
        return dailyPrices;
    }

    public void setDailyPrices(List<BigDecimal> dailyPrices) {
        this.dailyPrices = dailyPrices;
    }

    @Column(name = "previous_price", nullable = false)
    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }
}
