package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.repository.EmployerRepository;
import com.example.jakartaee.service.EmployerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class EmployerServiceImpl implements EmployerService, Serializable {

    private final EmployerRepository employerRepository;

    @Inject
    public EmployerServiceImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public Employer save(Employer employer) {
        return employerRepository.save(employer)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + employer));
    }

    @Override
    public Employer findById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Employer with id " + id + ", not found."));
    }

    @Override
    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    @Override
    public Employer delete(Long id) {
        Employer employer = findById(id);
        employerRepository.delete(employer);
        return employer;
    }

}
