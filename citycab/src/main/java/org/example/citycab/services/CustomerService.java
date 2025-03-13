package org.example.citycab.services;

import org.example.citycab.entities.Customer;
import org.example.citycab.entities.dao.CustomerDAO;


import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();


    public void addCustomer(Customer customer) throws Exception {
        customerDAO.saveCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer)  {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId)  {
        customerDAO.deleteCustomer(customerId);
    }
}
