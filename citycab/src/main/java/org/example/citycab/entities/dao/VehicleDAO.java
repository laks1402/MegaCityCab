package org.example.citycab.entities.dao;

import org.example.citycab.entities.Users;
import org.example.citycab.entities.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class VehicleDAO {

    private SessionFactory sessionFactory;

    public VehicleDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // assuming hibernate.cfg.xml is properly configured
                .addAnnotatedClass(Vehicle.class)
                .buildSessionFactory();
    }

    // Create or update a Vehicle
    public void saveOrUpdate(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(vehicle);
        transaction.commit();
        session.close();
    }

    // Get a Vehicle by ID
    public Vehicle getById(int id) {
        Session session = sessionFactory.openSession();
        Vehicle vehicle = session.get(Vehicle.class, id);
        session.close();
        return vehicle;
    }

    // Get all Vehicles
    public List<Vehicle> getAll() {
        Session session = sessionFactory.openSession();
        Query<Vehicle> query = session.createQuery("from Vehicle", Vehicle.class);
        List<Vehicle> vehicles = query.getResultList();
        session.close();
        return vehicles;
    }

    // Delete a Vehicle by ID
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Vehicle vehicle = session.get(Vehicle.class, id);
        if (vehicle != null) {
            session.delete(vehicle);
        }
        transaction.commit();
        session.close();
    }
}
