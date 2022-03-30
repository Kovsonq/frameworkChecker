package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Order;
import com.example.jakartaee.domain.dto.OrdersDetailsDto;
import com.example.jakartaee.domain.values.ScheduleDetails;
import com.example.jakartaee.ex.OrderCreateException;
import com.example.jakartaee.repository.OrderRepository;
import com.example.jakartaee.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class OrderServiceImpl implements OrderService, Serializable {

    private final OrderRepository orderRepository;

    @Inject
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + order));
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Order with id " + id + ", not found."));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order newOrder) {
        Order order = findById(newOrder.getId());

        order.setDescription(newOrder.getDescription());
        order.setBooked(newOrder.isBooked());
        order.setDuration(newOrder.getDuration());
        order.setStartDate(newOrder.getStartDate());
        order.setEndDate(newOrder.getEndDate());
        order.setEmployer(newOrder.getEmployer());
        order.setStatus(newOrder.getStatus());
        order.setUser(newOrder.getUser());

        return orderRepository.update(order)
                .orElseThrow(() -> new EntityExistsException("Error during updating entity " + order));
    }

    @Override
    public Order delete(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
        return order;
    }

    @Override
    public Order bookOrder(Long id) {
        Order order = findById(id);
        order.setBooked(true);
        return order;
    }

    @Override
    public Order releaseOrder(Long id) {
        Order order = findById(id);
        order.setBooked(false);
        return order;
    }

    @Override
    public List<Order> processOrders(List<Order> newOrders, List<Order> existedOrders) {
        List<ZonedDateTime> zonedDateTimeList = newOrders.stream().map(Order::getStartDate).collect(Collectors.toList());
        checkNewOrdersTimeToConflicts(existedOrders, zonedDateTimeList);

        List<Order> processedOrders = new ArrayList<>();
        newOrders.forEach(order -> processedOrders.add(new Order(order.getStartDate(), order.getEndDate(),
                order.getDescription())));
        return processedOrders;
    }

    @Override
    public List<Order> createOrders(OrdersDetailsDto dto, List<Order> orders) {
        int quantity = dto.getQuantity() == null ? 1 : dto.getQuantity();
        long pauseDuration = dto.getPauseDuration() == null ? 0 : dto.getPauseDuration();
        ZonedDateTime currentStartTime = dto.getStartDate();
        List<ZonedDateTime> zonedDateTimeList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            zonedDateTimeList.add(currentStartTime);
            currentStartTime = ChronoUnit.MINUTES.addTo(currentStartTime, dto.getDuration() + pauseDuration);
        }
        checkNewOrdersTimeToConflicts(orders, zonedDateTimeList);
        zonedDateTimeList.forEach(startTime -> orders.add(new Order(startTime, dto.getDuration())));
        return orders;
    }

    private void checkNewOrdersTimeToConflicts(List<Order> existedOrders, List<ZonedDateTime> scheduledTime) {
        List<ZonedDateTime> conflictTime = scheduledTime.stream()
                .filter(time -> isNewOrderHasConflict(existedOrders, time))
                .collect(Collectors.toList());

        if (!conflictTime.isEmpty()) {
            throw new OrderCreateException("Order has wrong details to be created. It has conflict with another one." +
                    " Conflict time: " + conflictTime);
        }
    }

    @Override
    public List<Order> scheduleOrders(List<Order> ordersToSchedule, List<Order> existedOrders,
                                      ScheduleDetails scheduleDetails) {
        LocalDate localStartDate = scheduleDetails.getStartDay().toLocalDate();
        LocalDate localEndDate = scheduleDetails.getEndDay().toLocalDate();

        List<Long> dayDiffCollector = localStartDate.datesUntil(localEndDate.plusDays(1))
                .filter(localDate -> scheduleDetails.getDaysOfWeek().contains(localDate.getDayOfWeek()))
                .map(localDate -> ChronoUnit.DAYS.between(localStartDate, localDate))
                .collect(Collectors.toList());
        List<ZonedDateTime> timeToSchedule = ordersToSchedule.stream()
                .map(Order::getStartDate)
                .map(zonedDateTime -> dayDiffCollector.stream()
                        .map(zonedDateTime::plusDays)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        checkNewOrdersTimeToConflicts(existedOrders, timeToSchedule);
        return addOrdersToDates(ordersToSchedule, dayDiffCollector);
    }

    private List<Order> addOrdersToDates(List<Order> ordersToSchedule, List<Long> listDaysBetween) {
        List<Order> scheduledOrderList = new ArrayList<>(ordersToSchedule);
        ordersToSchedule.forEach(order -> listDaysBetween
                .forEach(value -> scheduledOrderList.add(new Order(order.getStartDate().plusDays(value),
                        order.getDuration(), UUID.randomUUID()))
                )
        );
        return scheduledOrderList;
    }

    private boolean isNewOrderHasConflict(List<Order> orders, ZonedDateTime newOrderDate) {
        return orders.stream().anyMatch(order -> order.getStartDate().isAfter(newOrderDate)
                && order.getStartDate().isBefore(newOrderDate));
    }

}
