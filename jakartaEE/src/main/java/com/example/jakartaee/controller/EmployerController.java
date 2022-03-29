package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.domain.Service;
import com.example.jakartaee.domain.dto.OrdersDetailsDto;
import com.example.jakartaee.ex.ParsingException;
import com.example.jakartaee.service.EmployerService;
import com.example.jakartaee.service.OrderService;
import com.example.jakartaee.service.ServiceService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Path("/employers")
public class EmployerController {

    private final EmployerService employerService;
    private final OrderService orderService;
    private final ServiceService serviceService;

    @Inject
    public EmployerController(EmployerService employerService, OrderService orderService, ServiceService serviceService) {
        this.employerService = employerService;
        this.orderService = orderService;
        this.serviceService = serviceService;
    }

    @POST
    @Produces("application/json")
    public Employer createEmployer(@Valid Employer employer) {
        return employerService.save(employer);
    }

    @PUT
    @Produces("application/json")
    public Employer updateEmployer(@Valid Employer newEmployer) {
        return employerService.update(newEmployer);
    }

    @PUT
    @Path("/{id}/orders")
    @Produces("application/json")
    public Employer addOrdersToEmployer(@Valid List<Order> orders, @PathParam("id") Long id) {
        Employer employer = employerService.findById(id);
        List<Order> existedOrders = employer.getOrders();
        existedOrders.addAll(orderService.processOrders(orders, existedOrders));
        return employerService.update(employer);
    }

    @PUT
    @Path("/{id}/orders/details")
    @Produces("application/json")
    public Employer addOrdersToEmployerByDetails(@Valid OrdersDetailsDto dto, @PathParam("id") Long id) {
        Employer employer = employerService.findById(id);
        List<Order> existedOrders = employer.getOrders();
        List<Order> newOrders = orderService.createOrders(dto, existedOrders);

        if (dto.getScheduleDetails() != null) {
            List<Order> scheduleOrders = orderService.scheduleOrders(newOrders, existedOrders, dto.getScheduleDetails());
            newOrders.addAll(scheduleOrders);
        }
        existedOrders.addAll(newOrders);
        return employerService.update(employer);
    }

    @PUT
    @Path("/{employerId}/orders/{orderId}")
    @Produces("application/json")
    public Employer addOrderToEmployer(@PathParam("employerId") Long employerId,
                                       @PathParam("orderId") Long orderId) {
        Employer employer = employerService.findById(employerId);
        List<Order> existedOrders = employer.getOrders();

        if (existedOrders.stream().map(Order::getId).anyMatch(id -> Objects.equals(id, orderId))) {
            throw new EntityExistsException("Order with id:" + orderId + " already exists for Employer " + employerId);
        }
        existedOrders.add(orderService.findById(orderId));
        return employerService.update(employer);
    }

    @DELETE
    @Path("/{employerId}/orders/uuid={uuid}&from={from}&to={to}")
    @Produces("application/json")
    public Employer deleteOrdersFromEmployer(@PathParam("employerId") Long employerId,
                                             @PathParam("uuid") String uuid,
                                             @PathParam("from") String from,
                                             @PathParam("to") String to) {
        Employer employer = employerService.findById(employerId);
        List<Order> existedOrders = employer.getOrders();
        ZonedDateTime fromDate;
        ZonedDateTime toDate;
        UUID scheduleId;

        try {
            fromDate = from == null ? ZonedDateTime.from(LocalDate.MIN) : ZonedDateTime.parse(from);
            toDate = to == null ? ZonedDateTime.from(LocalDate.MAX) : ZonedDateTime.parse(to);
            scheduleId = uuid == null ? null : UUID.fromString(uuid);
        } catch (Exception exception) {
            throw new ParsingException("Parsing exception: " + from + " or " + to);
        }
        Stream<Order> filteredOrderStream = existedOrders.stream()
                .filter(order -> order.getStartDate().isAfter(fromDate) && order.getStartDate().isBefore(toDate));

        if (scheduleId != null) {
            filteredOrderStream = filteredOrderStream.filter(order -> scheduleId.equals(order.getScheduleId()));
        }
        filteredOrderStream.forEach(existedOrders::remove);
        return employerService.update(employer);
    }

    @DELETE
    @Path("/{employerId}/orders/{orderId}")
    @Produces("application/json")
    public Employer deleteOrderFromEmployer(@PathParam("employerId") Long employerId,
                                            @PathParam("orderId") Long orderId) {
        Employer employer = employerService.findById(employerId);
        List<Order> existedOrders = employer.getOrders();

        if (existedOrders.stream().map(Order::getId).noneMatch(id -> Objects.equals(id, orderId))) {
            throw new EntityExistsException("Order with id:" + orderId + " doesn't exist for Employer " + employerId);
        }
        existedOrders.remove(orderService.findById(orderId));
        return employerService.update(employer);
    }

    @PUT
    @Path("/{employerId}/services/{serviceId}")
    @Produces("application/json")
    public Employer addServiceToEmployer(@PathParam("employerId") Long employerId,
                                         @PathParam("serviceId") Long serviceId) {
        Employer employer = employerService.findById(employerId);
        List<Service> existedServices = employer.getServices();

        if (existedServices.stream().map(Service::getId).anyMatch(id -> Objects.equals(id, serviceId))) {
            throw new EntityExistsException("Service with id:" + serviceId + " already exists for Employer " + employerId);
        }
        existedServices.add(serviceService.findById(serviceId));
        return employerService.update(employer);
    }

    @DELETE
    @Path("/{employerId}/services/{serviceId}")
    @Produces("application/json")
    public Employer deleteServiceFromEmployer(@PathParam("employerId") Long employerId,
                                              @PathParam("serviceId") Long serviceId) {
        Employer employer = employerService.findById(employerId);
        List<Service> existedServices = employer.getServices();

        if (existedServices.stream().map(Service::getId).noneMatch(id -> Objects.equals(id, serviceId))) {
            throw new EntityExistsException("Service with id:" + serviceId + " doesn't exist for Employer " + employerId);
        }
        existedServices.remove(serviceService.findById(serviceId));
        return employerService.update(employer);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Employer findEmployer(@PathParam("id") Long id) {
        return employerService.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<Employer> findAllEmployer() {
        return employerService.findAll();
    }

    @POST
    @Path("/{id}")
    @Produces("application/json")
    public Employer deleteEmployer(@PathParam("id") Long id) {
        return employerService.delete(id);
    }

}