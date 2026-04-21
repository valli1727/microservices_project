package com.example.userservice.listener;

import com.example.userservice.dto.Order;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "orderQueue")
    public void receive(Order order) {

        System.out.println("===== ORDER DETAILS =====");
        System.out.println("User ID   : " + order.getUserId());
        System.out.println("Product   : " + order.getProduct());
        System.out.println("Quantity  : " + order.getQuantity());

        userRepository.findById(Long.valueOf(order.getUserId()))
                .ifPresentOrElse(user -> {

                    System.out.println("===== USER DETAILS =====");
                    System.out.println("User ID   : " + user.getId());
                    System.out.println("Name      : " + user.getName());
                    System.out.println("Email     : " + user.getEmail()); // if exists

                }, () -> {
                    System.out.println("User not found for ID: " + order.getUserId());
                });
    }}