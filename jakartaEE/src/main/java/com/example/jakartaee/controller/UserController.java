package com.example.jakartaee.controller;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/users")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Produces("application/json")
    public User createUser(@Valid User user) {
        return userService.save(user);
    }

    @PUT
    @Produces("application/json")
    public User updateUser(@Valid User user) {
        return userService.update(user);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public User findUserById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @GET
    @Path("/userName={userName}")
    @Produces("application/json")
    public User findUserByName(@PathParam("userName") String userName) {
        return userService.findByName(userName);
    }

    @GET
    @Path("/userEmail={userEmail}")
    @Produces("application/json")
    public User findUserByEmail(@PathParam("userEmail") String userEmail) {
        return userService.findByEmail(userEmail);
    }

    @GET
    @Produces("application/json")
    public List<User> findAllUser() {
        return userService.findAll();
    }

    @POST
    @Path("/{id}")
    @Produces("application/json")
    public User deleteUser(@PathParam("id") Long id) {
        return userService.delete(id);
    }

}