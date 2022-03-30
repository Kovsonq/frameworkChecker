package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Status;
import com.example.jakartaee.service.StatusService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
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
    public Status findStatusById(@PathParam("id") Long id) {
        return statusService.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<Status> findAllStatus() {
        return statusService.findAll();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Status deleteStatus(@PathParam("id") Long id) {
        return statusService.delete(id);
    }

    @PUT
    @Path("/{id}/approve")
    @Produces("application/json")
    public Status approveStatus(@PathParam("id") Long id) {
        return statusService.approve(id);
    }

    @PUT
    @Path("/{id}/cancel")
    @Produces("application/json")
    public Status cancelStatus(@PathParam("id") Long id) {
        return statusService.cancel(id);
    }

    @PUT
    @Path("/{id}/start")
    @Produces("application/json")
    public Status startStatus(@PathParam("id") Long id) {
        return statusService.start(id);
    }

    @PUT
    @Path("/{id}/finish")
    @Produces("application/json")
    public Status finishStatus(@PathParam("id") Long id) {
        return statusService.finish(id);
    }

    @PUT
    @Path("/{id}/close")
    @Produces("application/json")
    public Status closeStatus(@PathParam("id") Long id) {
        return statusService.close(id);
    }

}