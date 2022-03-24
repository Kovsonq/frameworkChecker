package com.example.jakartaee.service;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.repository.CompanyRepository;
import com.example.jakartaee.repository.impl.CompanyRepositoryJpa;
import com.example.jakartaee.service.inter.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class CompanyService implements Service<Company>, Serializable {

    private final CompanyRepository companyRepository;

    @Inject
    public CompanyService(CompanyRepositoryJpa companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company)  {
        return companyRepository.save(company);
    }

    @Override
    public Company find(Long id)  {
        return companyRepository.find(id);
    }

    @Override
    public List<Company> findAll() {
        return null;
    }

    @Override
    public Long delete(Long id)  {
        companyRepository.delete(id);
        return id;
    }

}
