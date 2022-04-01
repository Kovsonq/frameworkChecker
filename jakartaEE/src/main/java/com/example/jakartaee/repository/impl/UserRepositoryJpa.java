package com.example.jakartaee.repository.impl;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class UserRepositoryJpa implements UserRepository, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> save(User user) {
        try {
            em.persist(user);
            return Optional.of(user);
        } catch (final Exception e) {
            log.trace("[ERROR] Error during saving User.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        try {
            em.merge(user);
            return Optional.of(user);
        } catch (final Exception e) {
            log.trace("[ERROR] Error during updating User.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByName(String userName) {
        try {
            User user = em.createNamedQuery("User.findByName", User.class)
                    .setParameter("name", userName)
                    .getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException exception) {
            log.trace(exception.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        try {
            User user = em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", userEmail)
                    .getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException exception) {
            log.trace(exception.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Override
    public Optional<User> delete(User user) {
        try {
            em.remove(user);
            return Optional.of(user);
        } catch (final Exception e) {
            log.trace("[ERROR] Error during deleting User.", e);
        }
        return Optional.empty();
    }

}
