package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Customer;
import org.example.citycab.entities.Users;
import org.example.citycab.entities.dao.CustomerDAO;
import org.example.citycab.services.CustomerService;
import org.example.citycab.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;



@WebServlet(value = "/users")
public class UsersController extends HttpServlet {

    private final UsersService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CustomerService customerService;
    public UsersController() {
        this.userService = new UsersService();
        this.customerService = new CustomerService();
        // Replace with proper initialization if needed
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read JSON from request body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            JsonNode jsonNode = objectMapper.readTree(jsonBuffer.toString());

            Users user = new Users();
            user.setUsername(jsonNode.get("username").asText());
            user.setEmail(jsonNode.get("email").asText());
            user.setPassword(jsonNode.get("password").asText());
            user.setRole("user");

            Customer customer = new Customer();
            customer.setName(user.getUsername()); // Reuse username as name
            customer.setEmail(user.getEmail());   // Reuse email
            if (jsonNode.has("address")) {
                customer.setAddress(jsonNode.get("address").asText());
            }
            if (jsonNode.has("identityCard")) {
                customer.setIc(jsonNode.get("identityCard").asText());
            }
            if (jsonNode.has("phoneNumber")) {
                customer.setPhoneNumber(jsonNode.get("phoneNumber").asText());
            }

            // Set bidirectional relationship
            user.setCustomer(customer);
            customer.setUser(user);

            // Save the user (cascades to customer due to CascadeType.ALL)
            userService.saveOrUpdateUser(user);

            // Respond with status 201 Created and the User object
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(user));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding user and customer: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(Collections.singletonMap("error", errorMessage)));
            out.flush();
            e.printStackTrace(); // For debugging
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

            if (userIdParam == null || userIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"User ID is required\"}");
                return;
            }

            long userId;
            try {
                userId = Long.parseLong(userIdParam);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid User ID format\"}");
                return;
            }

            // Check if user exists before deleting
            Users user = userService.getUser(userId);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
                return;
            }

            // Delete the user
            userService.deleteUser(userId);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"User deleted successfully\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error deleting user: " + e.getMessage() + "\"}");
        }
    }


}
