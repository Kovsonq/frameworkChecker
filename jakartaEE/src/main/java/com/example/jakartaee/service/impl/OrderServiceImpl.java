package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.domain.dto.OrdersDetailsDto;
import com.example.jakartaee.domain.values.OrderStatus;
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
        return orderRepository.delete(order)
                .orElseThrow(() -> new EntityExistsException("Error during deleting entity " + order));
    }

    @Override
    public Order bookOrder(Long id) {
        Order order = findById(id);
        order.setBooked(true);
        order.getStatus().setRequestedTime();
        return order;
    }

    @Override
    public Order releaseOrder(Long id) {
        Order order = findById(id);
        order.setBooked(false);
        order.getStatus().setCanceledTime();
        return order;
    }

    @Override
    public Order reopenOrder(Long id) {
        Order order = findById(id);
        if (order.isBooked()) {
            throw new OrderCreateException("Order has wrong details to be reopened. It is booked yet.");
        }
        order.getStatus().resetTime();
        return update(order);
    }

    @Override
    public Order reattachEmployer(Long orderId, Employer employer) {
        Order order = findById(orderId);

        if (isOrderHasConflict(employer.getOrders(), order.getStartDate())){
            throw new OrderCreateException("Order has wrong details to be reattached. It has conflict with another one." +
                    " Conflict time: " + order.getStartDate());
        }
        order.setEmployer(employer);
        return update(order);
    }

    @Override
    public List<Order> processOrders(List<Order> newOrders, List<Order> existedOrders, Employer employer) {
        List<ZonedDateTime> zonedDateTimeList = newOrders.stream()
                .map(Order::getStartDate)
                .collect(Collectors.toList());
        checkNewOrdersTimeToConflicts(existedOrders, zonedDateTimeList);

        List<Order> processedOrders = new ArrayList<>();
        newOrders.forEach(order -> processedOrders.add(new Order(order.getStartDate(), order.getEndDate(),
                order.getDescription(), employer)));
        return processedOrders;
    }

    @Override
    public List<Order> constructOrders(OrdersDetailsDto dto, List<Order> existedOrders, Employer employer) {
        int quantity = dto.getQuantity() == null ? 1 : dto.getQuantity();
        long pauseDuration = dto.getPauseDuration() == null ? 0 : dto.getPauseDuration();
        ZonedDateTime currentStartTime = dto.getStartDate();
        List<ZonedDateTime> zonedDateTimeList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            zonedDateTimeList.add(currentStartTime);
            currentStartTime = ChronoUnit.MINUTES.addTo(currentStartTime, dto.getDuration() + pauseDuration);
        }
        checkNewOrdersTimeToConflicts(existedOrders, zonedDateTimeList);
        return zonedDateTimeList.stream()
                .map(startTime -> new Order(startTime, dto.getDuration(), employer))
                .collect(Collectors.toList());
    }

    private void checkNewOrdersTimeToConflicts(List<Order> existedOrders, List<ZonedDateTime> scheduledTime) {
        List<ZonedDateTime> conflictTime = scheduledTime.stream()
                .filter(time -> isOrderHasConflict(existedOrders, time))
                .collect(Collectors.toList());

        if (!conflictTime.isEmpty()) {
            throw new OrderCreateException("Order has wrong details to be created. It has conflict with another one." +
                    " Conflict time: " + conflictTime.stream()
                    .map(ZonedDateTime::toString)
                    .collect(Collectors.joining("-", "{", "}")));
        }
    }

    @Override
    public List<Order> scheduleOrders(List<Order> ordersToSchedule, List<Order> existedOrders,
                                      ScheduleDetails scheduleDetails, Employer employer) {
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
        return addOrdersToDates(ordersToSchedule, dayDiffCollector, employer);
    }

    private List<Order> addOrdersToDates(List<Order> ordersToSchedule, List<Long> listDaysBetween, Employer employer) {
        List<Order> scheduledOrderList = new ArrayList<>(ordersToSchedule);
        ordersToSchedule.forEach(order -> listDaysBetween
                .forEach(value -> scheduledOrderList.add(new Order(order.getStartDate().plusDays(value),
                        order.getDuration(), UUID.randomUUID(), employer))
                )
        );
        return scheduledOrderList;
    }

    private boolean isOrderHasConflict(List<Order> orders, ZonedDateTime newOrderDate) {
        return orders.stream()
                .filter(order -> !OrderStatus.CLOSED.equals(order.getStatus().getOrderStatus()))
                .anyMatch(order -> newOrderDate.isAfter(order.getStartDate().minusSeconds(1))
                        && newOrderDate.isBefore(order.getStartDate().plusSeconds(1)));
    }

}
