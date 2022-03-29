package com.example.jakartaee.controller;

import com.example.jakartaee.domain.Service;
import com.example.jakartaee.service.ServiceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.stream.Collectors;

@Path("/services")
public class ServiceController {

    private final ServiceService serviceService;

    @Inject
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @POST
    @Produces("application/json")
    public Service createService(@Valid Service service) {
        return serviceService.save(service);
    }

    @PUT
    @Produces("application/json")
    public Service updateService(@Valid Service service) {
        return serviceService.update(service);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Service findService(@PathParam("id") Long id) {
        return serviceService.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<Service> findAllService() {
        return serviceService.findAll();
    }

    @POST
    @Path("/{id}")
    @Produces("application/json")
    public Service deleteService(@PathParam("id") Long id) {
        return serviceService.delete(id);
    }

    @POST
    @Path("/deleteServices")
    @Produces("application/json")
    public List<Service> deleteServiceList(List<Long> serviceIdList) {
        return serviceIdList.stream()
                .map(serviceService::delete)
                .collect(Collectors.toList());
    }

}