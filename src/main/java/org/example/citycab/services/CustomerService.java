package org.example.citycab.services;

import org.example.citycab.entities.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CustomerService {

    private static SessionFactory factory;

    static {
        try {
            // Initialize Hibernate session factory
            factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Customer.class).buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    // CREATE: Add a new customer
    public void addCustomer(Customer customer) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // READ: Get a customer by ID
    public Customer getCustomer(int id) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = null;
        try {
            customer = session.get(Customer.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }

    // READ: Get all customers
    public List<Customer> getAllCustomers() {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> customers = null;
        try {
            customers = session.createQuery("from Customer", Customer.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customers;
    }

    // UPDATE: Update a customer's information
    public void updateCustomer(Customer customer) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // DELETE: Delete a customer by ID
    public void deleteCustomer(int id) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();

        // Create a new customer
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhoneNumber("123-456-7890");
        customer.setAddress("1234 Elm Street");
        customerService.addCustomer(customer);

        // Get all customers
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(c -> System.out.println(c.getName()));

        // Get a customer by ID
        Customer retrievedCustomer = customerService.getCustomer(1);
        System.out.println("Customer: " + retrievedCustomer.getName());

        // Update customer
        retrievedCustomer.setName("John Doe Jr.");
        customerService.updateCustomer(retrievedCustomer);

        // Delete a customer by ID
        customerService.deleteCustomer(1);
    }
}
