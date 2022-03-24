package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Company;

import java.util.List;

public interface CompanyRepository {

    Company save(Company company);

    Company find(Long id);

    List<Company> findAll();

    Long delete(Long id);

}
