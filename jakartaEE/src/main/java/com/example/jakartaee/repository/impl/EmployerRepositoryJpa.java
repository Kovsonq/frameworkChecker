package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.repository.EmployerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class EmployerRepositoryJpa implements EmployerRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employer> save(Employer employer)  {
        try {
            em.persist(employer);
            return Optional.of(employer);
        } catch (final Exception e){
            log.trace("[ERROR] Error during saving Employer.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employer> update(Employer employer) {
        try {
            em.merge(employer);
            return Optional.of(employer);
        } catch (final Exception e){
            log.trace("[ERROR] Error during updating Employer.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employer> findById(Long id)  {
        Employer employer = em.find(Employer.class, id);
        return employer != null ? Optional.of(employer) : Optional.empty();
    }

    @Override
    public List<Employer> findAll() {
        return em.createNamedQuery("Employer.findAll", Employer.class).getResultList();
    }

    @Override
    public Optional<Employer> delete(Employer employer) {
        try {
            em.remove(employer);
            return Optional.of(employer);
        } catch (final Exception e){
            log.trace("[ERROR] Error during deleting Employer.", e);
        }
        return Optional.empty();
    }

}
