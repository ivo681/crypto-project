package com.example.backend.service.impl;

import com.example.backend.model.Order;
import com.example.backend.model.enums.OrderStatusEnum;
import com.example.backend.repository.BankTransactionRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public OrderServiceImpl(OrderRepository orderRepository, BankTransactionRepository bankTransactionRepository, UserRepository userRepository, ModelMapper modelMapper, Random random) {
        this.orderRepository = orderRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }

//    @Override
//    public String createOrder(Long quantity, String productName, String userEmail) {
////        List<Product> availableProductsByName = this.productRepository.getAvailableProductsByName(productName, StatusEnum.AVAILABLE);
//
//        Order order = new Order();
//        order.setUser(this.userRepository.findByEmail(userEmail).get());
//        order.setOrderStatus(OrderStatusEnum.INCOMPLETE);
//        order.setDate(LocalDate.now());
//        order.setNumber(generateOrderNumber());
//        order = this.orderRepository.save(order);
//        for (int i = 0; i < quantity; i++) {
////            Product currentProduct = availableProductsByName.get(i);
////            currentProduct.setOrder(order);
////            this.productRepository.save(currentProduct);
//        }
//        order = this.orderRepository.save(order);
//        return order.getId();
//    }

//    @Override
//    public String getOrderDetails(String orderId) {
//        Order order = this.orderRepository.findById(orderId).get();
//        ArrayList<Product> products = new ArrayList<>(order.getProducts());
//        return String.format("%s x %s -> %s", products.size(), products
//                        .get(0).getName(),
//                (BigDecimal.valueOf(order.getProducts().size()).multiply(order.getProducts().stream().findFirst().get().getCurrentPrice())).toPlainString());
//    }

//    @Override
//    public BigDecimal getOrderTotal(String orderId) {
//        Order order = this.orderRepository.findById(orderId).get();
//        ArrayList<Product> products = new ArrayList<>(order.getProducts());
//        return (BigDecimal.valueOf(order.getProducts().size()).multiply(order.getProducts().stream().findFirst().get().getCurrentPrice()));
//    }
//
//    @Override
//    public void addUnsuccessfulTransaction(String orderId, String number) {
//        Order order = this.orderRepository.findById(orderId).get();
//        order.getBankTransactions().add(this.bankTransactionRepository.findById(number).get());
//        this.orderRepository.save(order);
//    }
//
//    @Override
//    public void placeSuccessfulOrder(String orderId, String transactionNumber) {
//        Order order = this.orderRepository.findById(orderId).get();
//        Set<BankTransaction> bankTransactions = order.getBankTransactions();
//        bankTransactions.add(this.bankTransactionRepository.findById(transactionNumber).get());
//        for (Product product : order.getProducts()) {
//            product.setStatus(StatusEnum.SOLD);
//            this.productRepository.save(product);
//        }
//        order.setOrderStatus(OrderStatusEnum.COMPLETE);
//        this.orderRepository.save(order);
//    }
//
    @Override
    public boolean isOrderNumberTaken(Long number) {
        return this.orderRepository.findByOrderNumber(number).isPresent();
    }
//
//    @Override
//    public List<OrderViewModel> getUserOrders(String userEmail) {
//        return this.orderRepository.findOrdersForCurrentUser(userEmail, OrderStatusEnum.COMPLETE).stream().map(order -> {
//            OrderViewModel orderViewModel = this.modelMapper.map(order, OrderViewModel.class);
//            orderViewModel.setProductName(order.getProducts().stream().findFirst().get().getName());
//            orderViewModel.setQuantity(order.getProducts().size());
//            orderViewModel.setTotalSum(getOrderTotal(order.getId()));
//            return orderViewModel;
//        }).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<OrderViewModel> getAllOrders() {
//        return this.orderRepository.findAll().stream().map(order -> {
//            OrderViewModel orderViewModel = this.modelMapper.map(order, OrderViewModel.class);
//            orderViewModel.setOrderStatus(order.getOrderStatus().name());
//            orderViewModel.setProductName(order.getProducts().stream().findFirst().get().getName());
//            orderViewModel.setQuantity(order.getProducts().size());
//            orderViewModel.setTotalSum(getOrderTotal(order.getId()));
//            orderViewModel.setUserName(order.getUser().getEmail());
//            return orderViewModel;
//        }).collect(Collectors.toList());
//    }

    private Long generateOrderNumber() {
        long orderNumber = (10000000 + this.random.nextInt(9000000));
        while (isOrderNumberTaken(orderNumber)) {
            orderNumber = (10000000 + this.random.nextInt(9000000));
        }
        return orderNumber;
    }
}
