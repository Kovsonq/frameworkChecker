package com.example.jakartaee.repository;

import com.example.jakartaee.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByName(String userName);

    Optional<User> findByEmail(String userEmail);

    List<User> findAll();

    Optional<User> update(User user);

    Optional<User> delete(User user);

}
