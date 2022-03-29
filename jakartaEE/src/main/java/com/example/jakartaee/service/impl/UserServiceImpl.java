package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Employer;
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
        checkUserDetails(user);
        return userRepository.save(user)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + user));
    }

    private void checkUserDetails(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(c -> {
                    throw new EntityExistsException("User with name " + c.getName() +
                            " already exist. Choose uniq one.");
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(c -> {
                    throw new EntityExistsException("User with email " + c.getEmail() +
                            " already exist. Choose uniq one.");
                });
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found."));
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User with name " + userName + " not found."));
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByName(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " not found."));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User newUser) {
        User user = findById(newUser.getId());

        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setOrders(newUser.getOrders());
        user.setPassword(newUser.getPassword());

        return userRepository.update(user)
                .orElseThrow(() -> new EntityExistsException("Error during updating entity " + user));
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
        return user;
    }

}
