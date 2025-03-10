import React, { useState, useEffect } from "react";
import "./customer.css";

const API_URL = "http://localhost:8091/citycab_war_exploded/customer";

const Customer = () => {
  const [customers, setCustomers] = useState([]);
  const [editingCustomer, setEditingCustomer] = useState(null);
  const [newCustomer, setNewCustomer] = useState({
    name: "",
    ic: "",
    email: "",
    phoneNumber: "",
    address: "",
  });
  const [showForm, setShowForm] = useState(false);

  // Fetch customers from backend
  useEffect(() => {
    fetch(API_URL)
      .then((res) => res.json())
      .then((data) => setCustomers(data))
      .catch((error) => console.error("Error fetching customers:", error));
  }, []);

  // Handle input changes
  const handleChange = (e) => {
    setNewCustomer({ ...newCustomer, [e.target.id]: e.target.value });
  };

  // Add new customer
  const handleAddCustomer = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newCustomer),
      });
      if (response.ok) {
        const addedCustomer = await response.json();
        setCustomers([...customers, addedCustomer]);
        setNewCustomer({ name: "", ic: "", email: "", phoneNumber: "", address: "" });
        setShowForm(false);
      } else {
        console.error("Failed to add customer");
      }
    } catch (error) {
      console.error("Error adding customer:", error);
    }
  };

  // Delete customer
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`${API_URL}?id=${id}`, {
        method: "DELETE",
      });
      if (response.ok) {
        setCustomers(customers.filter((customer) => customer.id !== id));
      } else {
        console.error("Failed to delete customer");
      }
    } catch (error) {
      console.error("Error deleting customer:", error);
    }
  };

  // Edit customer
  const handleEdit = (customer) => {
    setEditingCustomer({ ...customer });
  };

  // Update customer
  const handleUpdate = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(API_URL, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(editingCustomer),
      });
      if (response.ok) {
        setCustomers(
          customers.map((customer) =>
            customer.id === editingCustomer.id ? editingCustomer : customer
          )
        );
        setEditingCustomer(null);
      } else {
        console.error("Failed to update customer");
      }
    } catch (error) {
      console.error("Error updating customer:", error);
    }
  };

  return (
    <div className="customer-container">
      <h2>Customer Management</h2>
      <button className="corner-button" onClick={() => setShowForm(!showForm)}>
        {showForm ? "Hide Form" : "Add Customer"}
      </button>

      {showForm && (
        <form className="customer-form" onSubmit={handleAddCustomer}>
          <h2 className="form-title">Customer Details</h2>
          {Object.keys(newCustomer).map((key) => (
            <div className="form-group" key={key}>
              <label htmlFor={key}>{key.charAt(0).toUpperCase() + key.slice(1)}</label>
              <input
                type="text"
                id={key}
                placeholder={`Enter ${key}`}
                value={newCustomer[key]}
                onChange={handleChange}
              />
            </div>
          ))}
          <button type="submit" className="save-button">Save Details</button>
        </form>
      )}

      <table className="customer-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>IC</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Address</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.id}>
              <td>{customer.id}</td>
              <td>{customer.name}</td>
              <td>{customer.ic}</td>
              <td>{customer.email}</td>
              <td>{customer.phoneNumber}</td>
              <td>{customer.address}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(customer)}>
                  Edit
                </button>
                <button className="delete-button" onClick={() => handleDelete(customer.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingCustomer && (
        <div className="edit-form-container">
          <div className="edit-form">
            <button className="close-button" onClick={() => setEditingCustomer(null)}>✖</button>
            <h3>Edit Customer</h3>
            <form onSubmit={handleUpdate}>
              <div className="form-grid">
                {Object.keys(editingCustomer).map(
                  (key) =>
                    key !== "id" && (
                      <div className="form-group" key={key}>
                        <label htmlFor={key}>{key.charAt(0).toUpperCase() + key.slice(1)}</label>
                        <input
                          type="text"
                          id={key}
                          value={editingCustomer[key]}
                          onChange={(e) =>
                            setEditingCustomer({ ...editingCustomer, [key]: e.target.value })
                          }
                        />
                      </div>
                    )
                )}
              </div>
              <div className="form-actions">
                <button type="submit" className="save-button">Save</button>
                <button type="button" onClick={() => setEditingCustomer(null)} className="cancel-button">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Customer;
