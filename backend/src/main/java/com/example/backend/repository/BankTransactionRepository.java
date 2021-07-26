package com.example.backend.repository;

import com.example.backend.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, String> {
    @Query("SELECT bt FROM BankTransaction bt WHERE bt.number=:number")
    Optional<BankTransaction> findByNumber(Long number);
}
