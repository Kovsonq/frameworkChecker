package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.repository.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class CompanyRepositoryJpa implements CompanyRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Company> save(Company company)  {
        try {
            em.persist(company);
            return Optional.of(company);
        } catch (final Exception e){
            log.trace("[ERROR] Error during saving Company.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> findById(Long id)  {
        Company company = em.find(Company.class, id);
        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    public Optional<Company> findByName(String userName)  {
        Company company = em.createNamedQuery("Company.findByName", Company.class)
                .setParameter("name", userName)
                .getSingleResult();
        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    public List<Company> findAll() {
        return em.createNamedQuery("Company.findAll", Company.class).getResultList();
    }

    @Override
    public Optional<Company> update(Company company) {
        try {
            em.merge(company);
            return Optional.of(company);
        } catch (final Exception e){
            log.trace("[ERROR] Error during updating Company.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> delete(Company company) {
        try {
            em.remove(company);
            return Optional.of(company);
        } catch (final Exception e){
            log.trace("[ERROR] Error during deleting Company.", e);
        }
        return Optional.empty();
    }

}
