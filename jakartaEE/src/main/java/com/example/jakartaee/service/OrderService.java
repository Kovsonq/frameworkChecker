package com.example.jakartaee.service;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.domain.dto.OrdersDetailsDto;
import com.example.jakartaee.domain.values.ScheduleDetails;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    Order findById(Long id);

    List<Order> findAll();

    Order update(Order order);

    Order delete(Long id);

    Order bookOrder(Long id);

    Order releaseOrder(Long id);

    List<Order> processOrders(List<Order> newOrders, List<Order> existedOrders);

    List<Order> createOrders(OrdersDetailsDto dto, List<Order> orders);

    List<Order> scheduleOrders(List<Order> ordersToSchedule, List<Order> existedOrders, ScheduleDetails scheduleDetails);
}
