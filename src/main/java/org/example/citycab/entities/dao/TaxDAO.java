package org.example.citycab.entities.dao;

import org.example.citycab.entities.Tax;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TaxDAO {

    private SessionFactory factory;

    public TaxDAO() {
        // Initialize the Hibernate SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml") // assuming hibernate.cfg.xml is properly configured
                .addAnnotatedClass(Tax.class)
                .buildSessionFactory();
    }

    // Create or Update Tax
    public void saveOrUpdateTax(Tax tax) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(tax);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Read Tax by ID
    public Tax getTaxById(int taxId) {
        Session session = factory.getCurrentSession();
        Tax tax = null;

        try {
            session.beginTransaction();
            tax = session.get(Tax.class, taxId);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return tax;
    }

    // Get all Taxes
    public List<Tax> getAllTaxes() {
        Session session = factory.getCurrentSession();
        List<Tax> taxes = null;

        try {
            session.beginTransaction();
            Query<Tax> query = session.createQuery("from Tax", Tax.class);
            taxes = query.getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return taxes;
    }

    // Delete Tax
    public void deleteTax(int taxId) {
        Session session = factory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Tax tax = session.get(Tax.class, taxId);
            if (tax != null) {
                session.delete(tax);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void close() {
        factory.close();
    }
}
