package com.example.jakartaee.service;

import com.example.jakartaee.domain.Company;

import java.util.List;

public interface CompanyService {

    Company save(Company company);

    Company findById(Long id);

    List<Company> findAll();

    Company delete(Long id);

}
