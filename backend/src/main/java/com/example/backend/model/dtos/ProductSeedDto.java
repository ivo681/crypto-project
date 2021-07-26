package com.example.backend.model.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductSeedDto {
    @Expose
    private String productName;
    @Expose
    private BigDecimal price;

    public ProductSeedDto() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
