package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.repository.EmployerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class EmployerRepositoryJpa implements EmployerRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Employer save(Employer employer)  {
        em.persist(employer);
        return employer;
    }

    @Override
    public Employer find(Long id)  {
        return em.find(Employer.class, id);
    }

    @Override
    public List<Employer> findAll() {
        return em.createNamedQuery("Employer.findAll", Employer.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
