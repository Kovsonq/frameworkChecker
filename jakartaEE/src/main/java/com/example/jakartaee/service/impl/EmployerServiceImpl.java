package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.domain.Order;
import com.example.jakartaee.repository.EmployerRepository;
import com.example.jakartaee.repository.OrderRepository;
import com.example.jakartaee.service.EmployerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class EmployerServiceImpl implements EmployerService, Serializable {

    private final EmployerRepository employerRepository;
    private final OrderRepository orderRepository;

    @Inject
    public EmployerServiceImpl(EmployerRepository employerRepository, OrderRepository orderRepository) {
        this.employerRepository = employerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Employer save(Employer employer) {
        checkEmployerDetails(employer);
        return employerRepository.save(employer)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + employer));
    }

    private void checkEmployerDetails(Employer employer) {
        employerRepository.findByName(employer.getName())
                .ifPresent(c -> {
                    throw new EntityExistsException("Employer with name " + c.getName() +
                            " already exist. Choose uniq one.");
                });
        employerRepository.findByEmail(employer.getEmail())
                .ifPresent(c -> {
                    throw new EntityExistsException("Employer with email " + c.getEmail() +
                            " already exist. Choose uniq one.");
                });
    }

    @Override
    public Employer findById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Employer with id " + id + ", not found."));
    }

    @Override
    public Employer findByName(String employerName) {
        return employerRepository.findByName(employerName)
                .orElseThrow(() -> new EntityNotFoundException("Employer with name " + employerName + " not found."));
    }

    @Override
    public Employer findByEmail(String employerEmail) {
        return employerRepository.findByName(employerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Employer with email " + employerEmail + " not found."));
    }

    @Override
    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    @Override
    public Employer update(Employer newEmployer) {
        Employer existedEmployer = findById(newEmployer.getId());

        existedEmployer.setName(newEmployer.getName());
        existedEmployer.setEmail(newEmployer.getEmail());
        existedEmployer.setPhone(newEmployer.getPhone());
        existedEmployer.setCompany(newEmployer.getCompany());
        existedEmployer.setOrders(newEmployer.getOrders());
        existedEmployer.setServices(newEmployer.getServices());

        return employerRepository.update(existedEmployer)
                .orElseThrow(() -> new EntityExistsException("Error during updating entity " + existedEmployer));
    }

    @Override
    public Employer delete(Long id) {
        Employer employer = findById(id);
        List<Order> ordersToCancel = employer.getOrders().stream()
                .filter(Order::isBooked)
                .collect(Collectors.toList());
        employer.getOrders().removeAll(ordersToCancel);
        ordersToCancel.forEach(order -> {
            order.setEmployer(null);
            order.getStatus().setCanceledTime();
            orderRepository.update(order);
        });
        return employerRepository.delete(employer)
                .orElseThrow(() -> new EntityExistsException("Error during deleting entity " + employer));
    }

}
