package org.example.citycab.entities.dao;
import jakarta.transaction.Transactional;
import org.example.citycab.entities.Booking;
import org.example.citycab.entities.Customer;
import org.example.citycab.entities.Users;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

//import static org.example.citycab.utils.HibernateUtil.sessionFactory;


public class CustomerDAO {
    private SessionFactory factory;

    public CustomerDAO() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml") // assuming hibernate.cfg.xml is properly configured
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
    }
    @Transactional
    public void saveCustomer(Customer customer) throws Exception {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Manually set the User entity
            if (customer.getUser() != null && customer.getUser().getId() != null) {
                // Fetch the User from the database using the User's ID
                Users user = session.get(Users.class, customer.getUser().getId());
                if (user != null) {
                    customer.setUser(user);  // Set the fetched User to the Customer
                } else {
                    throw new Exception("User not found with ID: " + customer.getUser().getId());
                }
            } else {
                throw new Exception("User information is missing or invalid in the Customer data.");
            }

            // Save the customer
            session.save(customer);

            // Commit the transaction
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    // 🔹 Get Customer by ID
    public Customer getCustomerById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Customer.class, id);
        }
    }

    // 🔹 Get All Customers
    public List<Customer> getAllCustomers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    // 🔹 Update Customer
    public void updateCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public void deleteCustomer(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
                System.out.println("Customer deleted: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
