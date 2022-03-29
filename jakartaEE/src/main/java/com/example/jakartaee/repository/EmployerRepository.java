package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Employer;

import java.util.List;
import java.util.Optional;

public interface EmployerRepository {

    Optional<Employer> save(Employer employer);

    Optional<Employer> findById(Long id);

    List<Employer> findAll();

    Optional<Employer> update(Employer employer);

    Optional<Employer> delete(Employer employer);

}
