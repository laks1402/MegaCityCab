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
        // Initialize the Hibernate SessionFactory
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

            // Fetch existing references from DB to avoid transient object issues
            Customer customer = session.get(Customer.class, booking.getCustomer().getId());
            Vehicle vehicle = session.get(Vehicle.class, booking.getVehicle().getId());
            Tax tax = session.get(Tax.class, booking.getTax().getId());

            if (customer == null || vehicle == null || tax == null) {
                throw new IllegalArgumentException("Invalid foreign key references!");
            }

            // Set fetched entities to avoid transient issues
            booking.setCustomer(customer);
            booking.setVehicle(vehicle);
            booking.setTax(tax);

            // Fetch vehicle amount and multiply by 2
            double totalAmount = vehicle.getPrice() * 2;
//            booking.amount(totalAmount); // Assuming Booking has a setTotalAmount method

            // Save the booking first
            session.save(booking);

            // Create and associate payment
            Payment payment = new Payment();
            payment.setAmount(totalAmount);  // Set the doubled amount
            payment.setPaymentDate(new Date());  // Set current date as payment date
            payment.setPaymentMethod("Online");  // Example method (modify as needed)
            payment.setIsSuccessful(true);  // Assuming payment succeeds
            payment.setBooking(booking);  // Link payment to booking

            // Save the payment
            session.save(payment);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }



    public Booking getBookingById(Long id) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                return session.get(Booking.class, id);
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
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                Booking booking = session.get(Booking.class, id);
                if (booking != null) {
                    session.delete(booking);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }
    }


