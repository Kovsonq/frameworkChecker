package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Service;
import com.example.jakartaee.repository.ServiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class ServiceRepositoryJpa implements ServiceRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Service> save(Service service)  {
        try {
            em.persist(service);
            return Optional.of(service);
        } catch (final Exception e){
            log.trace("[ERROR] Error during saving Service.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Service> findById(Long id)  {
        Service service = em.find(Service.class, id);
        return service != null ? Optional.of(service) : Optional.empty();
    }

    @Override
    public List<Service> findAll() {
        return em.createNamedQuery("Service.findAll", Service.class).getResultList();
    }

    @Override
    public Optional<Service> delete(Service service) {
        try {
            em.remove(service);
            return Optional.of(service);
        } catch (final Exception e){
            log.trace("[ERROR] Error during deleting Service.", e);
        }
        return Optional.empty();
    }

}
