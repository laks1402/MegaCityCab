package org.example.citycab.services;

import org.example.citycab.entities.Feedback;
import org.example.citycab.entities.dao.FeedbackDAO;

import java.util.List;

public class FeedbackService {

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    // Method to save feedback
    public void saveFeedback(Feedback feedback) {
        feedbackDAO.saveFeedback(feedback);
    }

    // Method to update feedback
    public void updateFeedback(Feedback feedback) {
        feedbackDAO.updateFeedback(feedback);
    }

    // Method to delete feedback by ID
    public void deleteFeedback(int feedbackId) {
        feedbackDAO.deleteFeedback(feedbackId);
    }

    // Method to get feedback by ID
    public Feedback getFeedback(int feedbackId) {
        return feedbackDAO.getFeedbackById(feedbackId);
    }

    // Method to get all feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackDAO.getAllFeedbacks();
    }
}
