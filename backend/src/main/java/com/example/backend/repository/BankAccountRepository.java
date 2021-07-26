package com.example.backend.repository;

import com.example.backend.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    @Query("SELECT b FROM BankAccount b WHERE b.number=:number AND b.cvv=:cvv AND concat(b.firstName,' ',b.lastName)=:fullName" +
            " AND b.validTo >= :lower AND b.validTo < :upper")
    Optional<BankAccount> findAccountByDetails(String number, int cvv, String fullName, LocalDate lower, LocalDate upper);

    @Query("SELECT b FROM BankAccount b WHERE b.number=:number AND (b.balance-b.blockedAmount) > :orderTotal")
    Optional<BankAccount> checkIfAccountHasEnoughBalance(String number, BigDecimal orderTotal);

    @Query("SELECT b FROM BankAccount b WHERE b.number=:number")
    Optional<BankAccount> findByNumber(String number);
}
