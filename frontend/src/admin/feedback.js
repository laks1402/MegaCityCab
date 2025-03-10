import React, { useState, useEffect } from "react";
import axios from "axios";
import "./feedback.css";

const Feedback = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [editingFeedback, setEditingFeedback] = useState(null);
  const [newFeedback, setNewFeedback] = useState({
    customerName: "",
    feedbackText: "",
    rating: "",
  });
  const [showForm, setShowForm] = useState(false);

  // Fetch all feedbacks when the component mounts
  useEffect(() => {
    axios
      .get("http://localhost:8091/citycab_war_exploded/feedback")
      .then((response) => {
        setFeedbacks(response.data);
      })
      .catch((error) => {
        console.error("There was an error fetching feedbacks!", error);
      });
  }, []);

  // Handle adding new feedback
  const handleAddFeedback = (event) => {
    event.preventDefault();
    axios
      .post("http://localhost:8091/citycab_war_exploded/feedback", newFeedback)
      .then((response) => {
        setFeedbacks([...feedbacks, response.data]);
        setNewFeedback({ customerName: "", feedbackText: "", rating: "" });
      })
      .catch((error) => {
        console.error("There was an error adding the feedback!", error);
      });
  };

  // Handle deleting feedback
  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8091/citycab_war_exploded/feedback?id=${id}`)
      .then(() => {
        setFeedbacks(feedbacks.filter((feedback) => feedback.id !== id));
      })
      .catch((error) => {
        console.error("There was an error deleting the feedback!", error);
      });
  };

  // Handle editing feedback
  const handleEdit = (feedback) => {
    setEditingFeedback(feedback);
  };

  // Handle updating feedback
  const handleUpdate = (event) => {
    event.preventDefault();
    axios
      .put("http://localhost:8091/citycab_war_exploded/feedback", editingFeedback)
      .then(() => {
        setFeedbacks(
          feedbacks.map((feedback) =>
            feedback.id === editingFeedback.id ? editingFeedback : feedback
          )
        );
        setEditingFeedback(null);
      })
      .catch((error) => {
        console.error("There was an error updating the feedback!", error);
      });
  };

  return (
    <div className="feedback-container">
      <h2>Feedback Management</h2>
      <button className="corner-button" onClick={() => setShowForm(!showForm)}>
        {showForm ? "Hide Form" : "Add Feedback"}
      </button>
      {showForm && (
        <div className="feedback-form classic-form">
          <h2 className="form-title">Feedback Details</h2>
          <div className="form-group">
            <label htmlFor="customerName">Customer Name</label>
            <input
              type="text"
              id="customerName"
              placeholder="Enter customer name"
              value={newFeedback.customerName}
              onChange={(e) => setNewFeedback({ ...newFeedback, customerName: e.target.value })}
            />
          </div>
          <div className="form-group">
            <label htmlFor="feedbackText">Feedback</label>
            <textarea
              id="feedbackText"
              placeholder="Enter feedback"
              value={newFeedback.feedbackText}
              onChange={(e) => setNewFeedback({ ...newFeedback, feedbackText: e.target.value })}
            />
          </div>
         
          <button className="submit-button" onClick={handleAddFeedback}>
            Submit Feedback
          </button>
        </div>
      )}

      <table className="feedback-table">
        <thead>
          <tr>
            <th>Customer Name</th>
            <th>Feedback</th>
            <th>Rating</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {feedbacks.map((feedback) => (
            <tr key={feedback.id}>
              <td>{feedback.customerName}</td>
              <td>{feedback.feedbackText}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(feedback)}>
                  Edit
                </button>
                <button className="delete-button" onClick={() => handleDelete(feedback.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingFeedback && (
        <div className="edit-form">
          <h3>Edit Feedback</h3>
          <form onSubmit={handleUpdate}>
            <div className="form-grid">
              <div className="form-group">
                <label htmlFor="customerName">Customer Name</label>
                <input
                  type="text"
                  id="customerName"
                  value={editingFeedback.customerName}
                  onChange={(e) => setEditingFeedback({ ...editingFeedback, customerName: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="feedbackText">Feedback</label>
                <textarea
                  id="feedbackText"
                  value={editingFeedback.feedbackText}
                  onChange={(e) => setEditingFeedback({ ...editingFeedback, feedbackText: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="rating">Rating</label>
                <input
                  type="number"
                  id="rating"
                  value={editingFeedback.rating}
                  onChange={(e) => setEditingFeedback({ ...editingFeedback, rating: e.target.value })}
                  required
                />
              </div>
            </div>
            <div className="form-actions">
              <button type="submit">Update</button>
              <button type="button" onClick={() => setEditingFeedback(null)}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default Feedback;