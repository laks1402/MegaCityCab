import React, { useState, useEffect } from "react";
import axios from "axios";
import "./driver.css";

const Driver = () => {
  const [drivers, setDrivers] = useState([]);
  const [editingDriver, setEditingDriver] = useState(null);
  const [newDriver, setNewDriver] = useState({
    name: "",
    licenseNumber: "",
    phoneNumber: "",
  });
  const [showForm, setShowForm] = useState(false);

  // Fetch all drivers when the component mounts
  useEffect(() => {
    axios
      .get("http://localhost:8091/citycab_war_exploded/driver")
      .then((response) => {
        setDrivers(response.data);
      })
      .catch((error) => {
        console.error("There was an error fetching drivers!", error);
      });
  }, []);

  // Handle adding a new driver
  const handleAddDriver = (event) => {
    event.preventDefault();
    axios
      .post("http://localhost:8091/citycab_war_exploded/driver", newDriver)
      .then((response) => {
        setDrivers([...drivers, response.data]);
        setNewDriver({ name: "", licenseNumber: "", phoneNumber: "" });
      })
      .catch((error) => {
        console.error("There was an error adding the driver!", error);
      });
  };

  // Handle deleting a driver
  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8091/citycab_war_exploded/driver?id=${id}`)
      .then(() => {
        setDrivers(drivers.filter((driver) => driver.id !== id));
      })
      .catch((error) => {
        console.error("There was an error deleting the driver!", error);
      });
  };

  // Handle editing a driver
  const handleEdit = (driver) => {
    setEditingDriver(driver);
  };

  // Handle updating a driver's details
  const handleUpdate = (event) => {
    event.preventDefault();
    axios
      .put("http://localhost:8091/citycab_war_exploded/driver", editingDriver)
      .then(() => {
        setDrivers(
          drivers.map((driver) =>
            driver.id === editingDriver.id ? editingDriver : driver
          )
        );
        setEditingDriver(null);
      })
      .catch((error) => {
        console.error("There was an error updating the driver!", error);
      });
  };

  return (
    <div className="driver-container">
      <h2>Driver Management</h2>
      <button className="corner-button" onClick={() => setShowForm(!showForm)}>
        {showForm ? "Hide Form" : "Add Driver"}
      </button>
      {showForm && (
        <div className="driver-form classic-form">
          <h2 className="form-title">Driver Details</h2>
          {Object.keys(newDriver).map((key) => (
            <div className="form-group" key={key}>
              <label htmlFor={key}>
                {key.charAt(0).toUpperCase() + key.slice(1)}
              </label>
              <input
                type="text"
                id={key}
                placeholder={`Enter ${key}`}
                value={newDriver[key]}
                onChange={(e) =>
                  setNewDriver({ ...newDriver, [key]: e.target.value })
                }
              />
            </div>
          ))}
          <button className="submit-button" onClick={handleAddDriver}>
            Save Details
          </button>
        </div>
      )}

      <table className="driver-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>License Number</th>
            <th>Phone Number</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {drivers.map((driver) => (
            <tr key={driver.id}>
              <td>{driver.name}</td>
              <td>{driver.licenseNumber}</td>
              <td>{driver.phoneNumber}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(driver)}>
                  Edit
                </button>
                <button className="delete-button" onClick={() => handleDelete(driver.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingDriver && (
        <div className="edit-form">
          <h3>Edit Driver</h3>
          <form onSubmit={handleUpdate}>
            <div className="form-grid">
              {Object.keys(editingDriver).map(
                (key) =>
                  key !== "id" && (
                    <div className="form-group" key={key}>
                      <label htmlFor={key}>
                        {key.charAt(0).toUpperCase() + key.slice(1)}
                      </label>
                      <input
                        type="text"
                        id={key}
                        value={editingDriver[key]}
                        onChange={(e) =>
                          setEditingDriver({ ...editingDriver, [key]: e.target.value })
                        }
                        required
                      />
                    </div>
                  )
              )}
            </div>
            <div className="form-actions">
              <button type="submit">Update</button>
              <button type="button" onClick={() => setEditingDriver(null)}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default Driver;
