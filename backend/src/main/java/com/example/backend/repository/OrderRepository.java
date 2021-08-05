package com.example.backend.repository;

import com.example.backend.model.Order;
import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.number=:number")
    Optional<Order> findByOrderNumber(Long number);

    @Query("SELECT o FROM Order o WHERE o.number=:orderNumber AND o.user.email=:name " +
            "AND o.orderStatus= :incomplete")
    Optional<Order> findByOrderNumberAndLoggedUserAndStatus(Long orderNumber, String name, OrderStatusEnum incomplete);

    @Query("SELECT o FROM Order o WHERE o.number=:orderNumber AND o.orderStatus= :complete")
    Optional<Order> isOrderCompleted(Long orderNumber, OrderStatusEnum complete);

    @Query("SELECT o FROM Order o WHERE o.user.email=:username " +
            " AND o.orderStatus= :completed AND o.orderType = :type")
    List<Order> getCoinDetailsFromCompletedOrders(String username, OrderStatusEnum completed, OrderTypeEnum type);

    @Query("SELECT o FROM Order o WHERE o.user.email=:email AND o.coin.name=:capitalName" +
            " AND o.orderType=:type AND o.orderStatus=:status")
    List<Order> findOwnedCoinsByNameAndUserEmail(String capitalName, String email, OrderStatusEnum status, OrderTypeEnum type);

    @Query("SELECT o FROM Order o WHERE o.user.email=:userEmail AND o.orderStatus=:complete OR o.orderStatus=:declined" +
            " ORDER BY o.dateAndTime DESC")
    List<Order> getUserOperationsByEmailAndStatus(String userEmail, OrderStatusEnum complete, OrderStatusEnum declined);

    @Query("SELECT o FROM Order o WHERE o.orderStatus= :incomplete")
    List<Order> findByIncompleteStatus(OrderStatusEnum incomplete);
}
