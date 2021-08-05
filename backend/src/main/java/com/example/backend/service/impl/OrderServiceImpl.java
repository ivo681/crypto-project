package com.example.backend.service.impl;

import com.example.backend.model.BankTransaction;
import com.example.backend.model.Coin;
import com.example.backend.model.Order;
import com.example.backend.model.binding.OrderBindingModel;
import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.model.enums.OrderTypeEnum;
import com.example.backend.model.service.CoinServiceModel;
import com.example.backend.model.service.OperationServiceModel;
import com.example.backend.model.service.OrderServiceModel;
import com.example.backend.repository.BankTransactionRepository;
import com.example.backend.repository.CoinRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CoinRepository coinRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public OrderServiceImpl(OrderRepository orderRepository, CoinRepository coinRepository, BankTransactionRepository bankTransactionRepository, UserRepository userRepository, ModelMapper modelMapper, Random random) {
        this.orderRepository = orderRepository;
        this.coinRepository = coinRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public Long createOrder(OrderBindingModel orderBindingModel, String userEmail, OrderTypeEnum orderType) {
        Order order = new Order();
        order.setUser(this.userRepository.findByEmail(userEmail).get());
        order.setNumber(generateOrderNumber());
        order.setOrderStatus(OrderStatusEnum.INCOMPLETE);
        order.setCoin(this.coinRepository.findByName(orderBindingModel.getName()).get());
        order.setOrderType(orderType);
        double totalSum;
        if (orderType.equals(OrderTypeEnum.PURCHASE)){
            totalSum = orderBindingModel.getPrice().doubleValue()
                * orderBindingModel.getQuantity() * 1.05;
        } else{
            totalSum = orderBindingModel.getPrice().doubleValue()
                    * orderBindingModel.getQuantity() * 0.95;
        }
        order.setTotalSum(BigDecimal.valueOf(totalSum));
        order.setCoinQuantity(orderBindingModel.getQuantity());
        order.setDateAndTime(LocalDateTime.now());
        order = this.orderRepository.save(order);
        return order.getNumber();
    }

    @Override
    public BigDecimal getOrderTotal(Long orderNumber) {
        return this.orderRepository.findByOrderNumber(orderNumber).get().getTotalSum();
    }

    @Override
    public void changeStatusAndOwnership(Long orderId, String transactionNumber, OrderStatusEnum status) {
        Order order = this.orderRepository.findByOrderNumber(orderId).get();
        Set<BankTransaction> bankTransactions = order.getBankTransactions();
        bankTransactions.add(this.bankTransactionRepository.findById(transactionNumber).get());
        order.setOrderStatus(status);
        Coin coin = order.getCoin();
        coin.setQuantity(coin.getQuantity() - order.getCoinQuantity());
        this.coinRepository.save(coin);
        this.orderRepository.save(order);
    }

    @Override
    public boolean isOrderCompleted(Long orderNumber) {
        return this.orderRepository.isOrderCompleted(orderNumber, OrderStatusEnum.COMPLETE).isPresent();
    }

    @Override
    public List<CoinServiceModel> getOwnedUserCoins(String email) {
        List<Order> userPurchaseOrders = this.orderRepository.getCoinDetailsFromCompletedOrders(email, OrderStatusEnum.COMPLETE, OrderTypeEnum.PURCHASE);
        HashMap<String, CoinServiceModel> ownedCoins = new HashMap<>();
        for (Order order : userPurchaseOrders) {
            if (!ownedCoins.containsKey(order.getCoin().getName())){
                CoinServiceModel mappedModel = this.modelMapper.map(order.getCoin(), CoinServiceModel.class);
                mappedModel.setQuantity(0L);
                ownedCoins.put(order.getCoin().getName(), mappedModel);
            }
            CoinServiceModel serviceModel = ownedCoins.get(order.getCoin().getName());
            serviceModel.setQuantity(serviceModel.getQuantity() + order.getCoinQuantity());
            ownedCoins.put(order.getCoin().getName() , serviceModel);
        }
        List<Order> userSaleOrders = this.orderRepository.getCoinDetailsFromCompletedOrders(email, OrderStatusEnum.COMPLETE, OrderTypeEnum.SALE);
        for (Order order : userSaleOrders) {
            CoinServiceModel serviceModel = ownedCoins.get(order.getCoin().getName());
            serviceModel.setQuantity(serviceModel.getQuantity() - order.getCoinQuantity());
            ownedCoins.put(order.getCoin().getName() , serviceModel);
        }
        return new ArrayList<>(ownedCoins.values()).stream().filter(coin ->
            coin.getQuantity() > 0
        ).collect(Collectors.toList());
    }

    @Override
    public CoinServiceModel getOwnedCoinDetailsByNameAndEmail(String name, String email) {
        String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1);
        List<Order> purchaseOrders = this.orderRepository.
                findOwnedCoinsByNameAndUserEmail(capitalName, email, OrderStatusEnum.COMPLETE, OrderTypeEnum.PURCHASE);
        if (purchaseOrders.size() > 0){
            CoinServiceModel coinServiceModel = new CoinServiceModel();
            coinServiceModel.setName(capitalName);
            coinServiceModel.setCurrentPrice(purchaseOrders.get(0).getCoin().getCurrentPrice());
            coinServiceModel.setQuantity(0L);
            for (Order order : purchaseOrders) {
                coinServiceModel.setQuantity(coinServiceModel.getQuantity() + order.getCoinQuantity());
            }

            List<Order> saleOrders = this.orderRepository.
                    findOwnedCoinsByNameAndUserEmail(capitalName, email, OrderStatusEnum.COMPLETE, OrderTypeEnum.SALE);
            for (Order order : saleOrders) {
                coinServiceModel.setQuantity(coinServiceModel.getQuantity() - order.getCoinQuantity());
            }
            return coinServiceModel;
        }
        return null;
    }

    @Override
    public boolean isOwnedByUser(String coinName, String email) {
        String capitalName = coinName.substring(0, 1).toUpperCase() + coinName.substring(1);
        return this.orderRepository.
                findOwnedCoinsByNameAndUserEmail(capitalName,
                        email, OrderStatusEnum.COMPLETE, OrderTypeEnum.PURCHASE).size() > this.orderRepository.
                findOwnedCoinsByNameAndUserEmail(capitalName,
                        email, OrderStatusEnum.COMPLETE, OrderTypeEnum.SALE).size();
    }

    @Override
    public BigDecimal getTotalForSellingCoins(String coinName, String name) {
        String capitalName = coinName.substring(0, 1).toUpperCase() + coinName.substring(1);
        List<Order> purchaseOrders = this.orderRepository.
                findOwnedCoinsByNameAndUserEmail(capitalName, name, OrderStatusEnum.COMPLETE, OrderTypeEnum.PURCHASE);
        BigDecimal sum = BigDecimal.ZERO;
        for (Order order : purchaseOrders) {
            sum = sum.add(order.getCoin().getCurrentPrice().multiply(BigDecimal.valueOf(order.getCoinQuantity())));
        }
        List<Order> saleOrders = this.orderRepository.
                findOwnedCoinsByNameAndUserEmail(capitalName, name, OrderStatusEnum.COMPLETE, OrderTypeEnum.SALE);
        for (Order order : saleOrders) {
            sum = sum.subtract(order.getCoin().getCurrentPrice().multiply(BigDecimal.valueOf(order.getCoinQuantity())));
        }
        double commission = Math.abs(sum.doubleValue() - (sum.doubleValue() * 1.05));
        return sum.subtract(BigDecimal.valueOf(commission));
    }

    @Override
    public List<OperationServiceModel> getUserOperations(String userEmail) {
        List<OperationServiceModel> operationServiceModels = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        List<Order> successfulOrDeclinedOrders = this.orderRepository.getUserOperationsByEmailAndStatus(userEmail, OrderStatusEnum.COMPLETE, OrderStatusEnum.DECLINED);
        for (Order order : successfulOrDeclinedOrders) {
            for (BankTransaction bankTransaction : order.getBankTransactions()) {
                OperationServiceModel model = new OperationServiceModel();
                model.setDateTime(order.getDateAndTime().format(dateTimeFormatter));
                model.setPrice(order.getTotalSum());
                model.setOrderNumber(order.getNumber());
                model.setStatus(bankTransaction.getTransactionStatus());
                model.setOrderType(order.getOrderType());
                model.setCoinName(order.getCoin().getName());
                operationServiceModels.add(model);
            }
        }
        return operationServiceModels;
    }

    @Override
    public void clearIncompleteOrders() {
        List<Order> incompleteOrders = this.orderRepository.findByIncompleteStatus(OrderStatusEnum.INCOMPLETE);
        this.orderRepository.deleteAll(incompleteOrders);
    }

    @Override
    public boolean isOrderNumberTaken(Long number) {
        return this.orderRepository.findByOrderNumber(number).isPresent();
    }

    @Override
    public OrderServiceModel getOrderViewModel(Long orderNumber, String name) {
        Order order= this.orderRepository.
                findByOrderNumberAndLoggedUserAndStatus(
                        orderNumber, name, OrderStatusEnum.INCOMPLETE, OrderStatusEnum.DECLINED
                ).get();
        if (order == null){
            return null;
        }
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setOrderNumber(order.getNumber());
        orderServiceModel.setCoinName(order.getCoin().getName());
        orderServiceModel.setTotal(order.getTotalSum());
        orderServiceModel.setCoinQuantity(order.getCoinQuantity());
        return orderServiceModel;
    }

    private Long generateOrderNumber() {
        long orderNumber = (10000000 + this.random.nextInt(9000000));
        while (isOrderNumberTaken(orderNumber)) {
            orderNumber = (10000000 + this.random.nextInt(9000000));
        }
        return orderNumber;
    }
}
