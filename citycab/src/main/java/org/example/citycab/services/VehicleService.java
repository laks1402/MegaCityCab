package org.example.citycab.services;

import org.example.citycab.entities.Vehicle;
import org.example.citycab.entities.dao.VehicleDAO;

import java.util.List;

public class VehicleService {

    private VehicleDAO vehicleDAO;

    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    // Create or update a Vehicle
    public void saveOrUpdateVehicle(Vehicle vehicle) {
        vehicleDAO.saveOrUpdate(vehicle);
    }

    // Get a Vehicle by ID
    public Vehicle getVehicleById(int id) {
        return vehicleDAO.getById(id);
    }

    // Get all Vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAll();
    }

    // Delete a Vehicle by ID
    public void deleteVehicle(int id) {
        vehicleDAO.deleteById(id);
    }
}
