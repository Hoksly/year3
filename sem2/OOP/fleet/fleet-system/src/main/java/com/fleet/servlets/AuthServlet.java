package com.fleet.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth", "/auth/public-key"})
public class AuthServlet extends HttpServlet {



}