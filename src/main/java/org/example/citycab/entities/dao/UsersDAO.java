package org.example.citycab.entities.dao;

import org.example.citycab.entities.Tax;
import org.example.citycab.entities.Users;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UsersDAO {

    private SessionFactory sessionFactory;

    public UsersDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // assuming hibernate.cfg.xml is properly configured
                .addAnnotatedClass(Users.class)
                .buildSessionFactory();
    }

    // Create or Update User
    public void saveOrUpdateUser(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(user);

        transaction.commit();
    }

    // Get User by ID
    public Users getUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Users user = session.get(Users.class, id);

        transaction.commit();
        return user;
    }

    // Delete User
    public void deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Users user = session.get(Users.class, id);
        if (user != null) {
            session.delete(user);
        }

        transaction.commit();
    }

    // Get all Users
    public List<Users> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<Users> usersList = session.createQuery("from Users", Users.class).getResultList();

        transaction.commit();
        return usersList;
    }

    public void close() {
        sessionFactory.close();
    }
}
