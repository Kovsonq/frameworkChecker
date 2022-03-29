package com.example.jakartaee.service;

import com.example.jakartaee.domain.Company;

import java.util.List;

public interface CompanyService {

    Company save(Company company);

    Company findById(Long id);

    Company findByName(String userName);

    List<Company> findAll();

    Company update(Company company);

    Company delete(Long id);

}
