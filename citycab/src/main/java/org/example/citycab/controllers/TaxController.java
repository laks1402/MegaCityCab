package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Tax;
import org.example.citycab.services.TaxService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/tax")
public class TaxController extends HttpServlet {

    private final TaxService taxService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TaxController() {
        this.taxService = new TaxService();
    }

    // Create or Update Tax
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

            Tax tax = objectMapper.readValue(jsonBuffer.toString(), Tax.class);

            taxService.saveOrUpdateTax(tax);

            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(tax));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding/updating tax: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Read Tax by ID or Get All Taxes
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String taxIdParam = request.getParameter("id");
            if (taxIdParam != null) {
                // Get Tax by ID
                int taxId = Integer.parseInt(taxIdParam);
                Tax tax = taxService.getTaxById(taxId);

                if (tax != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    out.print(objectMapper.writeValueAsString(tax));
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = response.getWriter();
                    out.print("{\"error\": \"Tax not found\"}");
                    out.flush();
                }
            } else {
                // Get all Taxes
                List<Tax> taxes = taxService.getAllTaxes();
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(taxes));
                out.flush();
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error fetching tax(s): " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Update Tax
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

            Tax tax = objectMapper.readValue(jsonBuffer.toString(), Tax.class);

            taxService.saveOrUpdateTax(tax);

            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(tax));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error updating tax: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    // Delete Tax
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String taxIdParam = request.getParameter("id");
            if (taxIdParam != null) {
                // Get tax ID from the request
                int taxId = Integer.parseInt(taxIdParam);

                // Delete the tax using the service
                taxService.deleteTax(taxId);

                // Set status to 204 No Content (successful deletion)
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().flush();
            } else {
                // If ID is not provided, respond with an error
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"Tax ID is required\"}");
                response.getWriter().flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            String errorMessage = "Error deleting tax: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }
}
