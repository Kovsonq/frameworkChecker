package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Order;

import java.util.List;

public interface OrderRepository {

    Order find(Long id);

    List<Order> findAll();

}
