package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.service.CompanyService;
import com.example.jakartaee.service.inter.Service;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {

    private final Service<Company> companyService;

    @Inject
    public HelloResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        Company company = new Company("Name", "email");

        companyService.save(company);
        Company company1 = companyService.find(company.getId());

        return company1.toString();
    }
}