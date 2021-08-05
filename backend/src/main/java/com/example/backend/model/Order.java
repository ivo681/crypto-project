package com.example.backend.model;

import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private Coin coin;
    private Long coinQuantity;
    private User user;
    private Long number;
    private OrderStatusEnum orderStatus;
    private OrderTypeEnum orderType;
    private BigDecimal totalSum;
    private LocalDateTime dateAndTime;
    private Set<BankTransaction> bankTransactions = new HashSet<>();

    public Order() {
    }


    @ManyToOne
    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    @Column(name = "coin_quantity", nullable = false)
    public Long getCoinQuantity() {
        return coinQuantity;
    }

    public void setCoinQuantity(Long coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_bank_transactions",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="bank_transaction_id", referencedColumnName="id"))
    public Set<BankTransaction> getBankTransactions() {
        return bankTransactions;
    }

    public void setBankTransactions(Set<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    @Column(name = "number", nullable = false)
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Column(name = "total_sum", nullable = false)
    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }
}
