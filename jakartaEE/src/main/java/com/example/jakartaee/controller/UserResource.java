package com.example.jakartaee.controller;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/user")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces("application/json")
    public User createUser() {
        User user = new User("Alexius", "secret".toCharArray(), "+31584654", "email");
        userService.save(user);
        return userService.findById(user.getId());
    }

}