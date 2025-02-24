package org.example.citycab.services;

import org.example.citycab.entities.Booking;
import org.example.citycab.entities.dao.BookingDAO;

import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();

    public void addBooking(Booking booking) {
        bookingDAO.saveBooking(booking);
    }

    public Booking getBooking(Long id) {
        return bookingDAO.getBookingById(id);
    }

    public List<Booking> listAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public void modifyBooking(Booking booking) {
        bookingDAO.updateBooking(booking);
    }

    public void removeBooking(Long id) {
        bookingDAO.deleteBooking(id);
    }
}
