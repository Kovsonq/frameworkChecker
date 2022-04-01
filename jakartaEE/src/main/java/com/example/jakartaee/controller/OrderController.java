package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.service.EmployerService;
import com.example.jakartaee.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
public class OrderController {

    private final OrderService orderService;
    private final EmployerService employerService;

    @Inject
    public OrderController(OrderService orderService, EmployerService employerService) {
        this.orderService = orderService;
        this.employerService = employerService;
    }

    @POST
    @Produces("application/json")
    public Order createOrder(@Valid Order order) {
        return orderService.save(order);
    }

    @PUT
    @Produces("application/json")
    public Order updateOrder(@Valid Order order) {
        return orderService.update(order);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Order findOrder(@PathParam("id") Long id) {
        return orderService.findById(id);
    }

    @PUT
    @Path("{orderId}/employers/{employerId}")
    @Produces("application/json")
    public Order reattachOrderToEmployer(@PathParam("orderId") Long orderId,
                                            @PathParam("employerId") Long employerId) {
        Employer employer = employerService.findById(employerId);
        return orderService.reattachEmployer(orderId, employer);
    }

    @PUT
    @Path("{orderId}/reopen")
    @Produces("application/json")
    public Order reopenOrder(@PathParam("orderId") Long orderId) {
        return orderService.reopenOrder(orderId);
    }

    @GET
    @Produces("application/json")
    public List<Order> findAllOrder() {
        return orderService.findAll();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Order deleteOrder(@PathParam("id") Long id) {
        return orderService.delete(id);
    }

    @POST
    @Produces("application/json")
    public List<Order> deleteOrderList(List<Long> orderIdList) {
        return orderIdList.stream()
                .map(orderService::delete)
                .collect(Collectors.toList());
    }

}