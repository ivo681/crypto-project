package com.example.backend.model.service;

import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.enums.TransactionStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationServiceModel {
    private String userEmail;
    private String dateTime;
    private String coinName;
    private Long orderNumber;
    private OrderTypeEnum orderType;
    private BigDecimal price;
    private TransactionStatusEnum status;

    public OperationServiceModel() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
