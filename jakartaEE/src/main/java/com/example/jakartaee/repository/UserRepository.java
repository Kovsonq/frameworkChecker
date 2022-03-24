package com.example.jakartaee.repository;

import com.example.jakartaee.domain.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User find(Long id);

    List<User> findAll();

    Long delete(Long id);

}
