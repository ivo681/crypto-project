package com.example.backend.model.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankAccountSeedDto {
    @Expose
    private String number;
    @Expose
    private String idNumber;
    @Expose
    private LocalDate validFrom;
    @Expose
    private LocalDate validTo;
    @Expose
    private BigDecimal balance;
    @Expose
    private int cvv;
    @Expose
    private String firstName;
    @Expose
    private String lastName;

    public BankAccountSeedDto() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
