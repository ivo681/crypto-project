package com.example.backend.service;

import com.example.backend.model.binding.OrderBindingModel;
import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.service.CoinServiceModel;
import com.example.backend.model.service.OperationServiceModel;
import com.example.backend.model.service.OrderServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Long createOrder(OrderBindingModel orderBindingModel, String userEmail, OrderTypeEnum orderType);

    boolean isOrderNumberTaken(Long number);

    OrderServiceModel getOrderViewModel(Long orderNumber, String name);

    BigDecimal getOrderTotal(Long orderId);

    void changeStatusAndOwnership(Long orderId, String id, OrderStatusEnum status);

    boolean isOrderCompleted(Long orderNumber);

    List<CoinServiceModel> getOwnedUserCoins(String email);

    CoinServiceModel getOwnedCoinDetailsByNameAndEmail(String name, String email);

    boolean isOwnedByUser(String coinName, String name);

    BigDecimal getTotalForSellingCoins(String coinName, String name);

    List<OperationServiceModel> getUserOperations(String userEmail);

    List<OperationServiceModel> getAllUserOperations();

    List<OperationServiceModel> getUserOperationsFromToday();

    void clearIncompleteOrders();

//    List<OrderViewModel> getUserOrders(String userEmail);
//
//    List<OrderViewModel> getAllOrders();
}
