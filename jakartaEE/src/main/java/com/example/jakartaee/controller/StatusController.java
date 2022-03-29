package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Status;
import com.example.jakartaee.service.StatusService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/statuses")
public class StatusController {

    private final StatusService statusService;

    @Inject
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @POST
    @Produces("application/json")
    public Status createStatus(@Valid Status service) {
        return statusService.save(service);
    }

    @PUT
    @Produces("application/json")
    public Status updateStatus(@Valid Status service) {
        return statusService.update(service);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Status findStatus(@PathParam("id") Long id) {
        return statusService.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<Status> findAllStatus() {
        return statusService.findAll();
    }

    @POST
    @Path("/{id}")
    @Produces("application/json")
    public Status deleteStatus(@PathParam("id") Long id) {
        return statusService.delete(id);
    }

}