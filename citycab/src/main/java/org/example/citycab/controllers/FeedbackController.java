package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Feedback;
import org.example.citycab.services.FeedbackService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/feedback")
public class FeedbackController extends HttpServlet {

    private final FeedbackService feedbackService;
    private final ObjectMapper objectMapper;

    public FeedbackController() {
        this.feedbackService = new FeedbackService();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Support for LocalDate, etc.
    }

    // Create - POST
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
            Feedback feedback = objectMapper.readValue(jsonBuffer.toString(), Feedback.class);

            // Persist the feedback using the service
            feedbackService.saveFeedback(feedback);

            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(feedback));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error

            PrintWriter out = response.getWriter();
            String errorJson = objectMapper.writeValueAsString(
                    new ErrorResponse("Error adding feedback: " + e.getMessage())
            );
            out.print(errorJson);
            out.flush();
        }
    }

    // Read (Single feedback) - GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String feedbackIdParam = request.getParameter("id");
        if (feedbackIdParam != null) {
            int feedbackId = Integer.parseInt(feedbackIdParam);
            Feedback feedback = feedbackService.getFeedback(feedbackId);

            if (feedback != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(objectMapper.writeValueAsString(feedback));
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                PrintWriter out = response.getWriter();
                String errorJson = objectMapper.writeValueAsString(
                        new ErrorResponse("Feedback not found")
                );
                out.print(errorJson);
                out.flush();
            }
        } else {
            List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(feedbacks));
            out.flush();
        }
    }

    // Update - PUT
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
            Feedback feedback = objectMapper.readValue(jsonBuffer.toString(), Feedback.class);

            feedbackService.updateFeedback(feedback); // Update the feedback

            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(feedback));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            PrintWriter out = response.getWriter();
            String errorJson = objectMapper.writeValueAsString(
                    new ErrorResponse("Error updating feedback: " + e.getMessage())
            );
            out.print(errorJson);
            out.flush();
        }
    }

    // Delete - DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String feedbackIdParam = request.getParameter("id");
        if (feedbackIdParam != null) {
            int feedbackId = Integer.parseInt(feedbackIdParam);

            try {
                feedbackService.deleteFeedback(feedbackId); // Delete the feedback
                response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                PrintWriter out = response.getWriter();
                String errorJson = objectMapper.writeValueAsString(
                        new ErrorResponse("Error deleting feedback: " + e.getMessage())
                );
                out.print(errorJson);
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            PrintWriter out = response.getWriter();
            String errorJson = objectMapper.writeValueAsString(
                    new ErrorResponse("Missing feedback ID")
            );
            out.print(errorJson);
            out.flush();
        }
    }

    // Error response helper class
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
