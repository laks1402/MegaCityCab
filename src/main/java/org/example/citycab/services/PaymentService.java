package org.example.citycab.services;

import org.example.citycab.entities.Payment;
import org.example.citycab.entities.dao.PaymentDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PaymentService {

    private SessionFactory sessionFactory;

    public PaymentService() {
        // Create SessionFactory using Hibernate configuration
        this.sessionFactory = new Configuration().configure().addAnnotatedClass(Payment.class).buildSessionFactory();
    }

    // Create Payment
    public void createPayment(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            PaymentDAO paymentDAO = new PaymentDAO(session);
            paymentDAO.create(payment);
        }
    }

    // Get Payment by ID
    public Payment getPaymentById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            PaymentDAO paymentDAO = new PaymentDAO(session);
            return paymentDAO.getById(id);
        }
    }

    // Get all Payments
    public List<Payment> getAllPayments() {
        try (Session session = sessionFactory.openSession()) {
            PaymentDAO paymentDAO = new PaymentDAO(session);
            return paymentDAO.getAll();
        }
    }

    // Update Payment
    public void updatePayment(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            PaymentDAO paymentDAO = new PaymentDAO(session);
            paymentDAO.update(payment);
        }
    }

    // Delete Payment
    public void deletePayment(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            PaymentDAO paymentDAO = new PaymentDAO(session);
            paymentDAO.delete(payment);
        }
    }

    // Additional methods for business logic can be added here
}
