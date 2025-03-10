package org.example.citycab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.citycab.entities.Booking;
import org.example.citycab.entities.dao.BookingDAO;
import org.example.citycab.services.BookingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



@WebServlet("/booking")
public class BookingController extends HttpServlet {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;

    public BookingController() {
        this.bookingService = new BookingService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
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

            Booking booking = objectMapper.readValue(jsonBuffer.toString(), Booking.class);

            if (booking.getCustomer() == null || booking.getVehicle() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Customer and Driver cannot be null\"}");
                return;
            }

            bookingService.addBooking(booking);

            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(booking));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error adding booking: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Booking> bookings = bookingService.listAllBookings();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(bookings);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String bookingIdParam = request.getParameter("id");

            if (bookingIdParam == null || bookingIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Booking ID is required\"}");
                return;
            }

            Long bookingId;
            try {
                bookingId = Long.parseLong(bookingIdParam);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid booking ID format\"}");
                return;
            }

            // Call service method to delete booking
            bookingService.removeBooking(bookingId);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Booking deleted successfully\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error deleting booking: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Parse JSON request into Booking object
            Booking booking = objectMapper.readValue(request.getInputStream(), Booking.class);

            if (booking.getId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Booking ID is required\"}");
                return;
            }

            // Call service method to update booking
            bookingService.modifyBooking(booking);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Booking updated successfully\"}");

        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid request format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error updating booking: " + e.getMessage() + "\"}");
        }


    }



}