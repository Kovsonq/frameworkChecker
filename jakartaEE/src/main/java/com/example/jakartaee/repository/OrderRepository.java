package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    Optional<Order> update(Order order);

    Optional<Order> delete(Order order);

}
