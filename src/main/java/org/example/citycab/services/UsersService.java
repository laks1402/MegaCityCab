package org.example.citycab.services;

import org.example.citycab.entities.Users;
import org.example.citycab.entities.dao.UsersDAO;

import java.util.List;

public class UsersService {

    private UsersDAO usersDAO;

    public UsersService() {
        usersDAO = new UsersDAO();
    }

    // Save or Update User
    public void saveOrUpdateUser(Users user) {
        usersDAO.saveOrUpdateUser(user);
    }

    // Get User by ID
    public Users getUser(Long id) {
        return usersDAO.getUser(id);
    }

    // Delete User
    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

    // Get All Users
    public List<Users> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    // Close DAO connection
    public void close() {
        usersDAO.close();
    }
}
