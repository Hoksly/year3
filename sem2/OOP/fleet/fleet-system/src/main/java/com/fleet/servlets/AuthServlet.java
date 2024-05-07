package com.fleet.servlets;

import com.fleet.services.AuthService;

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
    private AuthService authService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.println(path);
        if ("/auth/public-key".equals(path)) {
            // Handle /auth/public-key endpoint
            handlePublicKey(request, response);
        }
    }

    private void handlePublicKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Send the public key to the client
        try {
            PublicKey publicKey = AuthService.getInstance().getPublicKey();
            byte[] publicKeyBytes = publicKey.getEncoded();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes);
            response.getWriter().write(publicKeyBase64);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request to decrypt a message
        String encryptedMessage = request.getParameter("message");
        if (encryptedMessage != null) {
            byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
            try {
                String decryptedMessage = authService.decryptMessage(encryptedMessageBytes);
                response.getWriter().write(decryptedMessage);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing 'message' parameter in the request.");
        }
    }
}