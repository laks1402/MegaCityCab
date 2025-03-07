package org.example.citycab.entities.dao;

import org.example.citycab.entities.Tax;
import org.example.citycab.entities.Users;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.HibernateException;
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
    public Users getUser(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Users user = session.get(Users.class, id);

        transaction.commit();
        return user;
    }

    public void deleteUser(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Fetch the user by ID
            Users user = session.get(Users.class, id);

            if (user != null) {
                session.delete(user);
            } else {
                System.out.println("User with ID " + id + " not found.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction on failure
            }
            e.printStackTrace();
        }
    }


    // Get all Users
    public List<Users> getAllUsers() {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            List<Users> usersList = session.createQuery("from Users", Users.class).getResultList();

            transaction.commit();
            return usersList;
        }catch (HibernateException e) {
            throw new HibernateException(e);
        }


    }

    public void close() {
        sessionFactory.close();

    }



}
