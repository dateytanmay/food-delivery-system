package com.limetray.assessment.service;

import com.limetray.assessment.DTO.OrderDTO;
import com.limetray.assessment.Entity.Order;
import com.limetray.assessment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueue orderQueue;

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setItems(orderDTO.getItems());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);
        orderQueue.addOrder(savedOrder.getId());
        return savedOrder;
    }

    @Override
    public Page<Order> getOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Order getOrderStatus(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
