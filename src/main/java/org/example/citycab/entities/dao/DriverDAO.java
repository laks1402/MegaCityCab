package org.example.citycab.entities.dao;

import org.example.citycab.entities.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.citycab.utils.HibernateUtil;

import java.util.List;

public class DriverDAO {
    private final SessionFactory sessionFactory;

    // Constructor to initialize the SessionFactory
    public DriverDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Method to create a new driver
    public void createDriver(Driver driver) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(driver);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to get a driver by ID
    public Driver getDriverById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Driver.class, id);
        }
    }

    // Method to get all drivers
    public List<Driver> getAllDrivers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Driver";
            Query<Driver> query = session.createQuery(hql, Driver.class);
            return query.list();
        }
    }

    // Method to update a driver's details
    public void updateDriver(Driver driver) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(driver);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to delete a driver by ID
    public void deleteDriver(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Driver driver = session.get(Driver.class, id);
            if (driver != null) {
                session.delete(driver);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to close the SessionFactory
    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}