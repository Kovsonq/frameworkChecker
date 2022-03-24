package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Status;
import com.example.jakartaee.repository.StatusRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class StatusRepositoryJpa implements StatusRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Status save(Status status)  {
        em.persist(status);
        return status;
    }

    @Override
    public Status find(Long id)  {
        return em.find(Status.class, id);
    }

    @Override
    public List<Status> findAll() {
        return em.createNamedQuery("Status.findAll", Status.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
