package org.example.citycab.entities.dao;
import jakarta.transaction.Transactional;
import org.example.citycab.entities.*;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Date;


import java.util.List;
public class BookingDAO {
    private SessionFactory factory;

    public BookingDAO() {

        factory = new Configuration()
                .configure("hibernate.cfg.xml") // assuming hibernate.cfg.xml is properly configured
                .addAnnotatedClass(Booking.class)
                .buildSessionFactory();
    }

    @Transactional
    public void saveBooking(Booking booking) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, booking.getCustomer().getId());
            Vehicle vehicle = session.get(Vehicle.class, booking.getVehicle().getId());
            Tax tax = session.get(Tax.class, booking.getTax().getId());

            if (customer == null || vehicle == null || tax == null) {
                throw new IllegalArgumentException("Invalid foreign key references!");
            }


            booking.setCustomer(customer);
            booking.setVehicle(vehicle);
            booking.setTax(tax);

            double totalAmount = vehicle.getPrice() * 2;


            session.save(booking);


            Payment payment = new Payment();
            payment.setAmount(totalAmount);
            payment.setPaymentDate(new Date());
            payment.setPaymentMethod("Online");
            payment.setIsSuccessful(true);
            payment.setBooking(booking);


            session.save(payment);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Booking", Booking.class).list();
        }
    }

    public void updateBooking(Booking booking) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteBooking(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Booking booking = session.get(Booking.class, id);


            if (booking != null) {

                session.remove(booking);
            } else {

                System.out.println("Booking with ID " + id + " not found.");

            }

            transaction.commit();
        } catch (Exception e) {


            e.printStackTrace();
        }
    }



}
