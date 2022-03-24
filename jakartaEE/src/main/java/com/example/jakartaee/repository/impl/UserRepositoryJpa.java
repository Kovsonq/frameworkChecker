package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UserRepositoryJpa implements UserRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user)  {
        em.persist(user);
        return user;
    }

    @Override
    public User find(Long id)  {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Override
    public Long delete(Long id)  {
        em.remove(find(id));
        return id;
    }

}
