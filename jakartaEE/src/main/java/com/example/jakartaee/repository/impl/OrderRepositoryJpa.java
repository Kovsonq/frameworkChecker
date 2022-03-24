package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Order;
import com.example.jakartaee.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class OrderRepositoryJpa implements OrderRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Order> save(Order order)  {
        try {
            em.persist(order);
            return Optional.of(order);
        } catch (final Exception e){
            log.trace("[ERROR] Error during saving Order.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Order> findById(Long id)  {
        Order order = em.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return em.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @Override
    public Optional<Order> delete(Order order) {
        try {
            em.remove(order);
            return Optional.of(order);
        } catch (final Exception e){
            log.trace("[ERROR] Error during deleting Order.", e);
        }
        return Optional.empty();
    }

}
