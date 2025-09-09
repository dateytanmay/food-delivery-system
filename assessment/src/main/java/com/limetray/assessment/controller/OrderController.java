package com.limetray.assessment.controller;

import com.limetray.assessment.DTO.OrderDTO;
import com.limetray.assessment.Entity.Order;
import com.limetray.assessment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.placeOrder(orderDTO);
    }

    @GetMapping
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrders(page, size);
    }

    @GetMapping("/{id}")
    public Order getOrderStatus(@PathVariable Long id) {
        return orderService.getOrderStatus(id);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }
}
