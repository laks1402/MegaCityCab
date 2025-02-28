package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Users;
import org.example.citycab.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



@WebServlet(value = "/users")
public class UsersController extends HttpServlet {

    private final UsersService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsersController() {
        this.userService = new UsersService(); // Replace with proper initialization if needed
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Deserialize JSON into a Users object
            Users user = objectMapper.readValue(jsonBuffer.toString(), Users.class);

            // Save or update the user using the service
            userService.saveOrUpdateUser(user);

            // Respond with status 201 Created and the User object
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(user));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding user: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String userIdParam = request.getParameter("id");

            if (userIdParam != null) {
                // Get user by ID
                long userId = Long.parseLong(userIdParam);
                Users user = userService.getUser((Long) userId);

                if (user != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    out.print(objectMapper.writeValueAsString(user));
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = response.getWriter();
                    out.print("{\"error\": \"User not found\"}");
                    out.flush();
                }
            } else {
                // Get all users
                List<Users> users = userService.getAllUsers();
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(users));
                out.flush();
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error fetching user(s): " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Deserialize JSON into a Users object
            Users user = objectMapper.readValue(jsonBuffer.toString(), Users.class);

            // Update the user using the service
            userService.saveOrUpdateUser(user);

            // Respond with status 200 OK and the updated User object
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(user));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error updating user: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String userIdParam = request.getParameter("id");
            if (userIdParam != null) {
                // Delete user by ID
                long userId = Long.parseLong(userIdParam);
                userService.deleteUser((int) userId);

                // Respond with status 204 No Content (successful deletion)
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().flush();
            } else {
                // If ID is not provided, respond with an error
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"User ID is required\"}");
                response.getWriter().flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error deleting user: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }
}
