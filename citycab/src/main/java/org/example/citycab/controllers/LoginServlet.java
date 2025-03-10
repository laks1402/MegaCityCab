package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.configs.JWTProvider;
import org.example.citycab.entities.Users;
import org.example.citycab.entities.dao.UsersDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginm")
public class LoginServlet extends HttpServlet {
//    @PersistenceContext(unitName = "your-persistence-unit-name")
    private UsersDAO em = new UsersDAO();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Users user = em.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                String token = JWTProvider.generateToken(user.getUsername(), user.getRole());

                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("token", token);
                responseMap.put("role", user.getRole());
                responseMap.put("username", user.getUsername());

                response.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(response.getWriter(), responseMap);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("error", "Invalid credentials");
                objectMapper.writeValue(response.getWriter(), errorMap);
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Authentication failed");
            objectMapper.writeValue(response.getWriter(), errorMap);
        }
    }
}
