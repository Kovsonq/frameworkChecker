package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Optional<Company> save(Company company);

    Optional<Company> findById(Long id);

    Optional<Company> findByName(String companyName);

    List<Company> findAll();

    Optional<Company> update(Company company);

    Optional<Company> delete(Company company);

}
