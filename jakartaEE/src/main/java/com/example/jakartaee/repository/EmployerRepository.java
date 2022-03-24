package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Employer;

import java.util.List;

public interface EmployerRepository {

    Employer save(Employer employer);

    Employer find(Long id);

    List<Employer> findAll();

    Long delete(Long id);

}
