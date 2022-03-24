package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.repository.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class CompanyRepositoryJpa implements CompanyRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Company save(Company company)  {
        em.persist(company);
        return company;
    }

    @Override
    public Company find(Long id)  {
        return em.find(Company.class, id);
    }

    @Override
    public List<Company> findAll() {
        return em.createNamedQuery("Company.findAll", Company.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
