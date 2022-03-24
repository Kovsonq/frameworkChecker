package com.example.jakartaee.service;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.repository.CompanyRepository;
import com.example.jakartaee.service.inter.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserService implements Service<User>, Serializable {

    private final CompanyRepository<User> userRepository;

    @Inject
    public UserService(CompanyRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user)  {
        userRepository.save(user);
        return user;
    }

    @Override
    public User find(Long id)  {
        return userRepository.find(id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Long delete(Long id)  {
        userRepository.delete(id);
        return id;
    }

}
