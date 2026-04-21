package com.example.orderservice.service;
import com.example.orderservice.config.RabbitConfig;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository repo, RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order createOrder(Order order) {

        String url = "http://USER-SERVICE/users/" + order.getUserId();

        try {
            restTemplate.getForObject(url, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("User not found");
        }

        Order savedOrder = repo.save(order);

        rabbitTemplate.convertAndSend("orderQueue", savedOrder);

        return savedOrder;
    }
    public List<Order> getAllOrders() {
        return repo.findAll();
    }
}