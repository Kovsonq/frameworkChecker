package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Service;
import com.example.jakartaee.repository.ServiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ServiceRepositoryJpa implements ServiceRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Service save(Service service)  {
        em.persist(service);
        return service;
    }

    @Override
    public Service find(Long id)  {
        return em.find(Service.class, id);
    }

    @Override
    public List<Service> findAll() {
        return em.createNamedQuery("Service.findAll", Service.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
