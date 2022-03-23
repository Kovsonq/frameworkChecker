package com.example.jakartaee;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.service.CompanyService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {

    private final CompanyService companyService;

    @Inject
    public HelloResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        Company user = new Company("Name", "email");

        boolean checkManager = companyService.checkManager();
        companyService.saveCompany(user);

        return user.toString();
    }
}