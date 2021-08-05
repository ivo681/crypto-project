package com.example.backend.repository;

import com.example.backend.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {

    @Query("SELECT c from Coin c WHERE c.quantity > 0 ORDER BY c.currentPrice DESC")
    List<Coin> getAllAvailableCoins();

    @Query("SELECT c FROM Coin c WHERE c.quantity > 0 AND c.name = :name")
    Optional<Coin> getAvailableCoinDetailsByName(String name);

    Optional<Coin> findByName(String name);
}
