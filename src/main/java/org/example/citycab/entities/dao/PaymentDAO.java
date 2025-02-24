package org.example.citycab.entities.dao;

import org.example.citycab.entities.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAO {

    private Session session;

    public PaymentDAO(Session session) {
        this.session = session;
    }

    // Create
    public void create(Payment payment) {
        Transaction transaction = session.beginTransaction();
        session.save(payment);
        transaction.commit();
    }

    // Read
    public Payment getById(Long id) {
        return session.get(Payment.class, id);
    }

    public List<Payment> getAll() {
        Query<Payment> query = session.createQuery("from Payment", Payment.class);
        return query.list();
    }

    // Update
    public void update(Payment payment) {
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
    }

    // Delete
    public void delete(Payment payment) {
        Transaction transaction = session.beginTransaction();
        session.delete(payment);
        transaction.commit();
    }


    public void close() {
        session.close();
    }
}
