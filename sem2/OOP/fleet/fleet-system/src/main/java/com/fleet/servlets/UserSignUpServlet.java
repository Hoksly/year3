package com.fleet.servlets;

import com.fleet.services.AuthService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

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

        // Parse the JSON object containing the encrypted data and key
        JSONObject json = new JSONObject(sb.toString());
        String encryptedData = json.getString("data");
        String encryptedKey = json.getString("key");

        try {
            // Decrypt the symmetric key using RSA private key
            String decryptedKey = AuthService.decryptSymmetricKey(encryptedKey);
            System.out.println("Decrypted key: " + decryptedKey);
            // Decrypt the data using the decrypted symmetric key
            String decryptedData = AuthService.decryptData(encryptedData, decryptedKey);

            System.out.println("Decrypted message: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
