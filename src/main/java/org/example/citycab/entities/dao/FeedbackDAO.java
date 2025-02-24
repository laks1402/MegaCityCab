package org.example.citycab.entities.dao;

import org.example.citycab.entities.Feedback;
import org.example.citycab.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class FeedbackDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // Create
    public void saveFeedback(Feedback feedback) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(feedback);  // Save feedback to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Read
    public Feedback getFeedbackById(int id) {
        Session session = sessionFactory.openSession();
        Feedback feedback = null;

        try {
            feedback = session.get(Feedback.class, id);  // Get feedback by id
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return feedback;
    }

    // Update
    public void updateFeedback(Feedback feedback) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(feedback);  // Update existing feedback
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Delete
    public void deleteFeedback(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Feedback feedback = session.get(Feedback.class, id);  // Get feedback by id

            if (feedback != null) {
                session.delete(feedback);  // Delete feedback from the database
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Get all feedbacks
    public List<Feedback> getAllFeedbacks() {
        Session session = sessionFactory.openSession();
        List<Feedback> feedbackList = null;

        try {
            feedbackList = session.createQuery("from Feedback", Feedback.class).list();  // Get all feedbacks
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return feedbackList;
    }
}
