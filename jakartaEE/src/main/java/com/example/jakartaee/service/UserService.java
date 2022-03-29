package com.example.jakartaee.service;

import com.example.jakartaee.domain.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findById(Long id);

    User findByName(String userName);

    User findByEmail(String userEmail);

    List<User> findAll();

    User update(User user);

    User delete(Long id);

}
