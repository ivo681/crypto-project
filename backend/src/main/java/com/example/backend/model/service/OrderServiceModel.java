package com.example.backend.model.service;

import java.math.BigDecimal;

public class OrderServiceModel {
    private String coinName;
    private Long coinQuantity;
    private BigDecimal total;
    private Long orderNumber;

    public OrderServiceModel() {
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getCoinQuantity() {
        return coinQuantity;
    }

    public void setCoinQuantity(Long coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
