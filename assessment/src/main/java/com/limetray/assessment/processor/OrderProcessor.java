package com.limetray.assessment.processor;

import com.limetray.assessment.repository.OrderRepository;
import com.limetray.assessment.service.OrderQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderQueue orderQueue;
    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 5000)
    public void processOrders() {
        Long orderId = orderQueue.pollOrder();
        if (orderId != null) {
            orderRepository.findById(orderId).ifPresent(order -> {
                order.setStatus("PROCESSED");
                orderRepository.save(order);
            });
        }
    }
}
