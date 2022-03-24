package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.repository.CompanyRepository;
import com.example.jakartaee.repository.impl.CompanyRepositoryJpa;
import com.example.jakartaee.service.CompanyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class CompanyServiceImpl implements CompanyService, Serializable {

    private final CompanyRepository companyRepository;

    @Inject
    public CompanyServiceImpl(CompanyRepositoryJpa companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + company));
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Company with id " + id + ", not found."));
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company delete(Long id) {
        Company Company = findById(id);
        companyRepository.delete(Company);
        return Company;
    }

}
