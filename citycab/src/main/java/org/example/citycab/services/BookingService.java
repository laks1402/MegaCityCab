package org.example.citycab.services;

import jakarta.transaction.Transactional;
import org.example.citycab.entities.Booking;
import org.example.citycab.entities.Payment;
import org.example.citycab.entities.dao.BookingDAO;

import java.util.Date;
import java.util.List;


public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();

    @Transactional
    public void addBooking(Booking booking) {
        bookingDAO.saveBooking(booking);
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
