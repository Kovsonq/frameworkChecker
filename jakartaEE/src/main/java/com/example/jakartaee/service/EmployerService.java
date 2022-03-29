package com.example.jakartaee.service;

import com.example.jakartaee.domain.Employer;

import java.util.List;

public interface EmployerService {

    Employer save(Employer employer);

    Employer findById(Long id);

    List<Employer> findAll();

    Employer update(Employer employer);

    Employer delete(Long id);

}
