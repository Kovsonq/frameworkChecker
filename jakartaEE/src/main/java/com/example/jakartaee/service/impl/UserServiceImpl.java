package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.repository.UserRepository;
import com.example.jakartaee.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserServiceImpl implements UserService, Serializable {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + user));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity User with id " + id + ", not found."));
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Entity User with name " + userName + ", not found."));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
        return user;
    }

}
