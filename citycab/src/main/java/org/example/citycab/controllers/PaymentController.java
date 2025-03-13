package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Payment;
import org.example.citycab.services.PaymentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentController() {
        this.paymentService = new PaymentService();
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


            Payment payment = objectMapper.readValue(jsonBuffer.toString(), Payment.class);

            paymentService.createPayment(payment);

            response.setStatus(HttpServletResponse.SC_CREATED);

            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(payment));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            PrintWriter out = response.getWriter();
            String errorMessage = "Error adding payment: " + e.getMessage();
            out.print(objectMapper.writeValueAsString(errorMessage));
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentIdParam = request.getParameter("id");

        if (paymentIdParam != null) {
            Long paymentId = Long.parseLong(paymentIdParam);
            Payment payment = paymentService.getPaymentById(paymentId);

            if (payment != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(payment));
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                PrintWriter out = response.getWriter();
                String errorJson = objectMapper.writeValueAsString(
                        new ErrorResponse("Payment not found")
                );
                out.print(errorJson);
                out.flush();
            }
        } else {
            try {
                List<Payment> payments = paymentService.getAllPayments();
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(payments));
                out.flush();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                PrintWriter out = response.getWriter();
                String errorJson = objectMapper.writeValueAsString(
                        new ErrorResponse("Error fetching payments: " + e.getMessage())
                );
                out.print(errorJson);
                out.flush();
            }
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
            Payment payment = objectMapper.readValue(jsonBuffer.toString(), Payment.class);

            paymentService.updatePayment(payment);

            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(payment));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            PrintWriter out = response.getWriter();
            String errorJson = objectMapper.writeValueAsString(
                    new ErrorResponse("Error updating payment: " + e.getMessage())
            );
            out.print(errorJson);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentIdParam = request.getParameter("id");
        if (paymentIdParam != null) {
            Long paymentId = Long.parseLong(paymentIdParam);

            try {
                Payment payment = paymentService.getPaymentById(paymentId);
                if (payment != null) {
                    paymentService.deletePayment(payment);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                    PrintWriter out = response.getWriter();
                    String errorJson = objectMapper.writeValueAsString(
                            new ErrorResponse("Payment not found")
                    );
                    out.print(errorJson);
                    out.flush();
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                PrintWriter out = response.getWriter();
                String errorJson = objectMapper.writeValueAsString(
                        new ErrorResponse("Error deleting payment: " + e.getMessage())
                );
                out.print(errorJson);
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            PrintWriter out = response.getWriter();
            String errorJson = objectMapper.writeValueAsString(
                    new ErrorResponse("Missing payment ID")
            );
            out.print(errorJson);
            out.flush();
        }
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
