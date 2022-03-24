package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.Order;
import com.example.jakartaee.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class OrderRepositoryJpa implements OrderRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order save(Order order)  {
        em.persist(order);
        return order;
    }

    @Override
    public Order find(Long id)  {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        return em.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
