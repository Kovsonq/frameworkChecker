package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Status;
import com.example.jakartaee.repository.StatusRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class StatusRepositoryJpa implements StatusRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Status> save(Status status)  {
        try {
            em.persist(status);
            return Optional.of(status);
        } catch (final Exception e){
            log.trace("[ERROR] Error during saving Status.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Status> findById(Long id)  {
        Status status = em.find(Status.class, id);
        return status != null ? Optional.of(status) : Optional.empty();
    }

    @Override
    public List<Status> findAll() {
        return em.createNamedQuery("Status.findAll", Status.class).getResultList();
    }

    @Override
    public Optional<Status> delete(Status status) {
        try {
            em.remove(status);
            return Optional.of(status);
        } catch (final Exception e){
            log.trace("[ERROR] Error during deleting Status.", e);
        }
        return Optional.empty();
    }

}
