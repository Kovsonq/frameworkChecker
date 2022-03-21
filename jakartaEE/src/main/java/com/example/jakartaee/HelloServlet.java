package com.example.jakartaee;

import com.example.jakartaee.domain.User;
import com.example.jakartaee.service.DbConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {

    EntityManager entityManager;

    private static final Gson GSON = new GsonBuilder().create();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String json = GSON.toJson(new User("Name","password".toCharArray(), "dsaf", "email"));

        Connection connection = DbConnection.getInstance();

        PrintWriter out = response.getWriter();
        out.println(json);
    }

    public void destroy() {
    }
}