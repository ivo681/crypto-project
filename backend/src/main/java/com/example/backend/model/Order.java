package com.example.backend.model;

import com.example.backend.model.enums.OrderStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
//    private Set<Coin> coins = new HashSet<>();
    private User user;
    private Long number;
    private OrderStatusEnum orderStatus;
    private LocalDate date;
    private Set<BankTransaction> bankTransactions = new HashSet<>();

    public Order() {
    }


//    @OneToMany(mappedBy = "coins", targetEntity = Coin.class,
//            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    public Set<Coin>  getCoins() {
//        return coins;
//    }

//    public void setCoins(Set<Coin>  coins) {
//        this.coins = coins;
//    }

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
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
