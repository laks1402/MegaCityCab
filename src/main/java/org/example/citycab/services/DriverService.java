package org.example.citycab.services;

import org.example.citycab.entities.Driver;
import org.example.citycab.entities.dao.DriverDAO;
import java.util.List;

public class DriverService {

    private DriverDAO driverDAO;

    public DriverService() {
        driverDAO = new DriverDAO();
    }

    // Create a new driver
    public void createDriver(Driver driver) {
        driverDAO.createDriver(driver);  // Calls the DAO method to save the driver
    }

    // Get a driver by ID
    public Driver getDriver(int id) {
        return driverDAO.getDriverById(id);  // Calls the DAO method to get the driver by ID
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();  // Calls the DAO method to get all drivers
    }

    // Update an existing driver
    public void updateDriver(Driver driver) {
        driverDAO.updateDriver(driver);  // Calls the DAO method to update the driver
    }

    // Delete a driver by ID
    public void deleteDriver(int id) {
        driverDAO.deleteDriver(id);  // Calls the DAO method to delete the driver by ID
    }
}
