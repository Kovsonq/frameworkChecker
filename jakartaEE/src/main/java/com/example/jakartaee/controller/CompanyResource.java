package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.service.CompanyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/company")
public class CompanyResource {

    private final CompanyService companyService;

    @Inject
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        Company company = new Company("Name", "email");
        companyService.save(company);
        Company company1 = companyService.findById(company.getId());
        return company1.toString();
    }
}