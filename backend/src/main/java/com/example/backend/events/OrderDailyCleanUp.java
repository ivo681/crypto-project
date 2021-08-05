package com.example.backend.events;

import com.example.backend.service.CoinService;
import com.example.backend.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderDailyCleanUp {
    private OrderService orderService;

    public OrderDailyCleanUp(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    @Scheduled(cron = "0 57 23 * * *")
    public void removeIncompleteOrders(){
        this.orderService.clearIncompleteOrders();
    }
}
