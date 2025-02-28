package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Driver;
import org.example.citycab.services.DriverService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List; //

@WebServlet("/driver")
public class    DriverController extends HttpServlet {
    private final DriverService driverService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DriverController() {
        this.driverService = new DriverService(); // Replace with proper initialization if using DI
    }

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

            // Deserialize JSON into a Driver object
            Driver driver = objectMapper.readValue(jsonBuffer.toString(), Driver.class);

            // Save the driver using the service
            driverService.createDriver(driver);

            // Set status to 201 Created
            response.setStatus(HttpServletResponse.SC_CREATED);

            // Write the Driver object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(driver));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding driver: " + e.getMessage();
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
            String driverIdParam = request.getParameter("id");
            if (driverIdParam != null) {
                // Get driver by ID
                int driverId = Integer.parseInt(driverIdParam);
                Driver driver = driverService.getDriver(driverId);

                if (driver != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    out.print(objectMapper.writeValueAsString(driver));
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = response.getWriter();
                    out.print("{\"error\": \"Driver not found\"}");
                    out.flush();
                }
            } else {
                // Get all drivers
                List<Driver> drivers = driverService.getAllDrivers();
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(drivers));
                out.flush();
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error fetching driver(s): " + e.getMessage();
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
            // Read the request body (JSON) into a string
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Deserialize JSON into a Driver object
            Driver driver = objectMapper.readValue(jsonBuffer.toString(), Driver.class);

            // Update the driver using the service
            driverService.updateDriver(driver);

            // Set status to 200 OK
            response.setStatus(HttpServletResponse.SC_OK);

            // Write the updated Driver object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(driver));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error updating driver: " + e.getMessage();
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
            String driverIdParam = request.getParameter("id");
            if (driverIdParam != null) {
                // Get driver ID from the request
                int driverId = Integer.parseInt(driverIdParam);

                // Delete the driver using the service
                driverService.deleteDriver(driverId);

                // Set status to 204 No Content (successful deletion)
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().flush();
            } else {
                // If ID is not provided, respond with an error
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"Driver ID is required\"}");
                response.getWriter().flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error deleting driver: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }
}
