package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Vehicle;
import org.example.citycab.services.VehicleService;
import org.example.citycab.entities.dao.VehicleDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/vehicle")
public class VehicleController extends HttpServlet {
    private final VehicleService vehicleService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VehicleController() {
        this.vehicleService = new VehicleService(new VehicleDAO());
    }

    // Create or update a Vehicle
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

            // Deserialize JSON into a Vehicle object
            Vehicle vehicle = objectMapper.readValue(jsonBuffer.toString(), Vehicle.class);

            // Save or update the vehicle using the service
            vehicleService.saveOrUpdateVehicle(vehicle);

            // Set status to 201 Created
            response.setStatus(HttpServletResponse.SC_CREATED);

            // Write the Vehicle object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(vehicle));
            out.flush();

        } catch (Exception e) {
            // Set status to 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding vehicle: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Get a single Vehicle by ID or get all vehicles
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String vehicleIdParam = request.getParameter("id");
            if (vehicleIdParam != null) {
                // Get vehicle by ID
                int vehicleId = Integer.parseInt(vehicleIdParam);
                Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

                if (vehicle != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    out.print(objectMapper.writeValueAsString(vehicle));
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = response.getWriter();
                    out.print("{\"error\": \"Vehicle not found\"}");
                    out.flush();
                }
            } else {
                // Get all vehicles
                List<Vehicle> vehicles = vehicleService.getAllVehicles();
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(vehicles));
                out.flush();
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error fetching vehicle(s): " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Update an existing Vehicle
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

            // Deserialize JSON into a Vehicle object
            Vehicle vehicle = objectMapper.readValue(jsonBuffer.toString(), Vehicle.class);

            // Update the vehicle using the service
            vehicleService.saveOrUpdateVehicle(vehicle);

            // Set status to 200 OK
            response.setStatus(HttpServletResponse.SC_OK);

            // Write the updated Vehicle object back as JSON in the response
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(vehicle));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error updating vehicle: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Delete a Vehicle by ID
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String vehicleIdParam = request.getParameter("id");
            if (vehicleIdParam != null) {
                // Get vehicle ID from the request
                int vehicleId = Integer.parseInt(vehicleIdParam);

                // Delete the vehicle using the service
                vehicleService.deleteVehicle(vehicleId);

                // Set status to 204 No Content (successful deletion)
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().flush();
            } else {
                // If ID is not provided, respond with an error
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"Vehicle ID is required\"}");
                response.getWriter().flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error deleting vehicle: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }
}
