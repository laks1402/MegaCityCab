package org.example.citycab.services;

import org.example.citycab.entities.Users;
import org.example.citycab.entities.dao.UsersDAO;

import java.util.List;

public class UsersService {

    private UsersDAO usersDAO;

    public UsersService() {
        usersDAO = new UsersDAO();
    }

    // Authenticate User
    public Users authenticateUser(String username, String password) {
        List<Users> users = usersDAO.getAllUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Add a method to check the user's role
    public String getUserRole(String username, String password) {
        Users user = authenticateUser(username, password);  // Pass both username and password
        if (user != null) {
            // Assuming the 'Users' class has a 'role' field (e.g., 'admin' or 'user')
            return user.getRole(); // Returns 'admin' or 'user'
        }
        return null;  // Return null if user is not found
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
    public void deleteUser(long id) {
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
