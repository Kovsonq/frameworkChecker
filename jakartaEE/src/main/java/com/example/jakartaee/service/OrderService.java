package com.example.jakartaee.service;

import com.example.jakartaee.domain.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    Order findById(Long id);

    List<Order> findAll();

    Order delete(Long id);

}
