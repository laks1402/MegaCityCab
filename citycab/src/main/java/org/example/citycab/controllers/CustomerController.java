package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Customer;
import org.example.citycab.services.CustomerService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/customer")
public class CustomerController extends HttpServlet {
    private final CustomerService customerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerController() {
        this.customerService = new CustomerService();
    }

    // CREATE: Add a new customer
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read the request body (JSON) into a string
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Deserialize JSON into a Customer object
            Customer customer = objectMapper.readValue(jsonBuffer.toString(), Customer.class);

            // Save the customer using the service
            customerService.addCustomer(customer);

            // Set status to 201 Created
            response.setStatus(HttpServletResponse.SC_CREATED);

            // Write the Customer object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(customer));
            out.flush();

        } catch (Exception e) {
            // Set status to 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // Write error message as JSON
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding customer: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // READ: Get a customer by ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String customerIdParam = request.getParameter("id");
            if (customerIdParam != null) {
                int customerId = Integer.parseInt(customerIdParam);
                Customer customer = customerService.getCustomerById(customerId);

                if (customer != null) {
                    // Customer found, return as JSON
                    PrintWriter out = response.getWriter();
                    out.print(objectMapper.writeValueAsString(customer));
                    out.flush();
                } else {
                    // Customer not found
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = response.getWriter();
                    out.print("{\"message\": \"Customer not found\"}");
                    out.flush();
                }
            } else {
                // If no ID is provided, return all customers
                List<Customer> customers = customerService.getAllCustomers();
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(customers));
                out.flush();
            }
        } catch (Exception e) {
            // Set status to 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error retrieving customer: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // UPDATE: Update customer information
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read the request body (JSON) into a string
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Deserialize JSON into a Customer object
            Customer customer = objectMapper.readValue(jsonBuffer.toString(), Customer.class);

            // Update the customer using the service
            customerService.updateCustomer(customer);

            // Write the updated Customer object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(customer));
            out.flush();

        } catch (Exception e) {
            // Set status to 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // Write error message as JSON
            PrintWriter out = response.getWriter();
            String errorMessage = "Error updating customer: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // DELETE: Delete a customer by ID
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String customerIdParam = request.getParameter("id");
            if (customerIdParam != null) {
                int customerId = Integer.parseInt(customerIdParam);

                // Delete the customer using the service
                customerService.deleteCustomer(customerId);

                // Set status to 200 OK
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print("{\"message\": \"Customer deleted successfully\"}");
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = response.getWriter();
                out.print("{\"message\": \"Customer ID is required\"}");
                out.flush();
            }
        } catch (Exception e) {
            // Set status to 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error deleting customer: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }


}
