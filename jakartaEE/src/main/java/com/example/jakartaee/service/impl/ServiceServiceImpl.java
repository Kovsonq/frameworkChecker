package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Service;
import com.example.jakartaee.repository.ServiceRepository;
import com.example.jakartaee.service.ServiceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class ServiceServiceImpl implements ServiceService, Serializable {

    private final ServiceRepository serviceRepository;

    @Inject
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + service));
    }

    @Override
    public Service findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Service with id " + id + ", not found."));
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service delete(Long id) {
        Service service = findById(id);
        serviceRepository.delete(service);
        return service;
    }

}
