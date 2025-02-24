package org.example.citycab.services;

import org.example.citycab.entities.Tax;
import org.example.citycab.entities.dao.TaxDAO;

import java.util.List;

public class TaxService {

    private TaxDAO taxDAO;

    public TaxService() {
        taxDAO = new TaxDAO();  // Initialize the TaxDAO instance
    }

    // Create or Update Tax
    public void saveOrUpdateTax(Tax tax) {
        taxDAO.saveOrUpdateTax(tax);
    }

    // Get Tax by ID
    public Tax getTaxById(int taxId) {
        return taxDAO.getTaxById(taxId);
    }

    // Get All Taxes
    public List<Tax> getAllTaxes() {
        return taxDAO.getAllTaxes();
    }

    // Delete Tax by ID
    public void deleteTax(int taxId) {
        taxDAO.deleteTax(taxId);
    }

    public void close() {
        taxDAO.close();
    }
}
