package com.fleet.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.models.UserRegistrationModel;
import com.fleet.services.KeycloakAdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "DriverSignUpServlet", urlPatterns = "/driver-sign-up")
public class UserSignUpServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PUT request received");
        System.out.println(req.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post request received");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        System.out.println(sb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        UserRegistrationModel user = objectMapper.readValue(sb.toString(), UserRegistrationModel.class);

        KeycloakAdminService serv = new KeycloakAdminService();
        serv.createKeycloakUser(user);
    }
}
