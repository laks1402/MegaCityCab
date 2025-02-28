package org.example.citycab.services;

import org.example.citycab.entities.Customer;
import org.example.citycab.entities.dao.CustomerDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();


    // CREATE: Add a new customer
    public void addCustomer(Customer customer) throws Exception {
        customerDAO.saveCustomer(customer);
    }

    // READ: Get a customer by ID
    public Customer getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    // READ: Get all customers
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // UPDATE: Update customer information
    public void updateCustomer(Customer customer)  {
        customerDAO.updateCustomer(customer);
    }

    // DELETE: Delete a customer by ID
    public void deleteCustomer(int customerId)  {
        customerDAO.deleteCustomer(customerId);
    }
}
