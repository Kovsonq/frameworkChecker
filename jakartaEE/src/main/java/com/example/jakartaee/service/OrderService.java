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

    Order reopenOrder(Long id);

    Order reattachEmployer(Long orderId, Employer employer);

    List<Order> processOrders(List<Order> newOrders, List<Order> existedOrders, Employer employer);

    List<Order> constructOrders(OrdersDetailsDto dto, List<Order> orders, Employer employer);

    List<Order> scheduleOrders(List<Order> ordersToSchedule, List<Order> existedOrders,
                               ScheduleDetails scheduleDetails, Employer employer);
}
