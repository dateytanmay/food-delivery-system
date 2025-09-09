package com.limetray.assessment.service;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class OrderQueue {

    private final Queue<Long> queue = new ConcurrentLinkedQueue<>();

    public void addOrder(Long orderId) {
        queue.offer(orderId);
    }

    public Long pollOrder() {
        return queue.poll();
    }
}
