package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Company;
import com.example.jakartaee.domain.Employer;
import com.example.jakartaee.service.CompanyService;
import com.example.jakartaee.service.EmployerService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.Objects;

@Path("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final EmployerService employerService;

    @Inject
    public CompanyController(CompanyService companyService, EmployerService employerService) {
        this.companyService = companyService;
        this.employerService = employerService;
    }

    @POST
    @Produces("application/json")
    public Company createCompany(@Valid Company company) {
        return companyService.save(company);
    }

    @PUT
    @Produces("application/json")
    public Company updateCompany(@Valid Company company) {
        return companyService.update(company);
    }

    @PUT
    @Path("/{companyId}/{employerId}")
    @Produces("application/json")
    public Company addEmployerToCompany(@PathParam("companyId") Long companyId,
                                        @PathParam("employerId") Long employerId) {
        Company company = companyService.findById(companyId);
        List<Employer> employers = company.getEmployers();

        if (employers.stream().map(Employer::getId).anyMatch(id -> Objects.equals(id, employerId))) {
            throw new EntityExistsException("Employer with id:" + employerId + " already exists in the company " + company);
        }
        Employer employer = employerService.findById(employerId);
        employers.add(employer);
        return companyService.update(company);
    }

    @DELETE
    @Path("/{companyId}/{employerId}")
    @Produces("application/json")
    public Company deleteEmployerFromCompany(@PathParam("companyId") Long companyId,
                                             @PathParam("employerId") Long employerId) {
        Company company = companyService.findById(companyId);
        List<Employer> employers = company.getEmployers();

        if (employers.stream().map(Employer::getId).noneMatch(id -> Objects.equals(id, employerId))) {
            throw new EntityExistsException("Employer with id:" + employerId + " doesn't exist in the company " + company);
        }
        Employer employer = employerService.findById(employerId);
        employers.remove(employer);
        return companyService.update(company);
    }

    @GET
    @Produces("application/json")
    public List<Company> findAllCompanies() {
        return companyService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Company findCompanyById(@PathParam("id") Long id) {
        return companyService.findById(id);
    }

    @GET
    @Path("/name={name}")
    @Produces("application/json")
    public Company findCompanyByName(@PathParam("name") String name) {
        return companyService.findByName(name);
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Company deleteCompany(@PathParam("id") Long id) {
        return companyService.delete(id);
    }

}