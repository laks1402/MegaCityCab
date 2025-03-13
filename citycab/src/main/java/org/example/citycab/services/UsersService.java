package org.example.citycab.services;

import org.example.citycab.entities.Users;
import org.example.citycab.entities.dao.UsersDAO;

import java.util.List;

public class UsersService {

    private UsersDAO usersDAO;

    public UsersService() {
        usersDAO = new UsersDAO();
    }



    public void saveOrUpdateUser(Users user) {
        usersDAO.saveOrUpdateUser(user);
    }


    public Users getUser(Long id) {
        return usersDAO.getUser(id);
    }


    public void deleteUser(long id) {
        usersDAO.deleteUser(id);
    }


    public List<Users> getAllUsers() {
        return usersDAO.getAllUsers();
    }


    public void close() {
        usersDAO.close();
    }
}
