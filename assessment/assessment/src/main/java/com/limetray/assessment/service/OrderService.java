package com.limetray.assessment.service;

import com.limetray.assessment.DTO.OrderDTO;
import com.limetray.assessment.Entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
    Page<Order> getOrders(int page, int size);
    Order getOrderStatus(Long id);
    Order updateOrderStatus(Long id, String status);
}
