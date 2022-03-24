package com.example.jakartaee.controller;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.service.UserService;
import com.example.jakartaee.service.inter.Service;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/user")
public class UserResource {

    private final Service<User> userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        User user = new User("Alexius", "secret".toCharArray(), "+31584654", "email");

        userService.save(user);
        User user1 = userService.find(user.getId());

        return user1.toString();
    }
}