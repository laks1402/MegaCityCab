package org.example.citycab.entities.dao;
import org.example.citycab.entities.Booking;
import org.example.citycab.entities.Customer;
import org.example.citycab.entities.Driver;
import org.example.citycab.entities.Tax;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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

    public void saveBooking(Booking booking) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            // Fetch existing references from DB to avoid transient object issues
            Customer customer = session.get(Customer.class, booking.getCustomer().getId());
            Driver driver = session.get(Driver.class, booking.getDriver().getId());
            Tax tax = session.get(Tax.class, booking.getTax().getId());

            if (customer == null || driver == null || tax == null) {
                throw new IllegalArgumentException("Invalid foreign key references!");
            }

            booking.setCustomer(customer);
            booking.setDriver(driver);
            booking.setTax(tax);

            session.save(booking);
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


