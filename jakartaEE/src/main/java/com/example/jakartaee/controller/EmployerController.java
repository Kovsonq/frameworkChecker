package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.domain.Service;
import com.example.jakartaee.domain.dto.OrdersDeleteDto;
import com.example.jakartaee.domain.dto.OrdersDetailsDto;
import com.example.jakartaee.ex.OrderRemoveException;
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
import java.util.stream.Collectors;
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
        List<Order> orderList = orderService.processOrders(orders, existedOrders, employer);
        orderList.forEach(orderService::save);
        return employerService.findById(id);
    }

    @PUT
    @Path("/{id}/orders/details")
    @Produces("application/json")
    public Employer addOrdersToEmployerByDetails(@Valid OrdersDetailsDto dto, @PathParam("id") Long id) {
        Employer employer = employerService.findById(id);
        List<Order> existedOrders = employer.getOrders();
        List<Order> newOrders = orderService.constructOrders(dto, existedOrders, employer);

        if (dto.getScheduleDetails() != null) {
            List<Order> scheduleOrders = orderService.scheduleOrders(newOrders, existedOrders,
                    dto.getScheduleDetails(), employer);
            newOrders.addAll(scheduleOrders);
        }
        existedOrders.addAll(newOrders);
        return employerService.update(employer);
    }

    @PUT
    @Path("/{id}/orders/delete")
    @Produces("application/json")
    public Employer deleteOrdersFromEmployer(@PathParam("id") Long id,
                                             @Valid OrdersDeleteDto dto) {
        Employer employer = employerService.findById(id);
        List<Order> existedOrders = employer.getOrders();
        ZonedDateTime fromDate;
        ZonedDateTime toDate;
        UUID scheduleId;

        try {
            fromDate = dto.getStartDate() == null ? ZonedDateTime.from(LocalDate.MIN) : ZonedDateTime.parse(dto.getStartDate());
            toDate = dto.getEndDate() == null ? ZonedDateTime.from(LocalDate.MAX) : ZonedDateTime.parse(dto.getEndDate());
            scheduleId = dto.getScheduleId() == null ? null : UUID.fromString(dto.getScheduleId());
        } catch (Exception exception) {
            throw new ParsingException("Parsing exception: " + dto + "Cause: " + exception.getMessage());
        }
        Stream<Order> filteredOrderStream = existedOrders.stream()
                .filter(order -> order.getStartDate().isAfter(fromDate) && order.getStartDate().isBefore(toDate));

        if (scheduleId != null) {
            filteredOrderStream = filteredOrderStream.filter(order -> scheduleId.equals(order.getScheduleId()));
        }
        List<Order> ordersToDelete = filteredOrderStream.collect(Collectors.toList());
        List<Order> ordersCantBeDeleted = ordersToDelete.stream()
                .filter(Order::isBooked)
                .collect(Collectors.toList());
        ordersToDelete.removeAll(ordersCantBeDeleted);
        ordersToDelete.stream().map(Order::getId).forEach(orderService::delete);

        if (!ordersCantBeDeleted.isEmpty()) {
            throw new OrderRemoveException("Deleted " + ordersToDelete.size() + " orders." +
                    "Orders are already booked and couldn't be deleted: " + ordersCantBeDeleted);
        }
        return employerService.findById(id);
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
    @Path("/employerName={employerName}")
    @Produces("application/json")
    public Employer findUserByName(@PathParam("employerName") String employerName) {
        return employerService.findByName(employerName);
    }


    @GET
    @Produces("application/json")
    public List<Employer> findAllEmployer() {
        return employerService.findAll();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Employer deleteEmployer(@PathParam("id") Long id) {
        return employerService.delete(id);
    }

}