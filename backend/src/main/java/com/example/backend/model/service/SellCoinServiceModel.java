package com.example.backend.model.service;

import com.example.backend.model.binding.OrderBindingModel;

public class SellCoinServiceModel {
    private Long orderNumber;
    private CoinServiceModel coinModel;

    public SellCoinServiceModel() {
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public CoinServiceModel getCoinModel() {
        return coinModel;
    }

    public void setCoinModel(CoinServiceModel coinModel) {
        this.coinModel = coinModel;
    }
}
