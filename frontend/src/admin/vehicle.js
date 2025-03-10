import React, { useState, useEffect } from "react";
import "./vehicle.css";

const API_URL = "http://localhost:8091/citycab_war_exploded/vehicle";

const Vehicle = () => {
  const [vehicles, setVehicles] = useState([]);
  const [editingVehicle, setEditingVehicle] = useState(null);
  const [newVehicle, setNewVehicle] = useState({
    cabModel: "",
    licensePlate: "",
    year: "",
    make: "",
    color: "",
    engine: "",
    fuelType: "",
    seatCount: "",  // New field
    price: "",      // New field
  });
  const [showForm, setShowForm] = useState(false);

  // Fetch vehicles from backend
  useEffect(() => {
    fetch(API_URL)
      .then((res) => res.json())
      .then((data) => setVehicles(data))
      .catch((error) => console.error("Error fetching vehicles:", error));
  }, []);

  // Handle input changes
  const handleChange = (e) => {
    setNewVehicle({ ...newVehicle, [e.target.id]: e.target.value });
  };

  // Add new vehicle
  const handleAddVehicle = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newVehicle),
      });
      if (response.ok) {
        const addedVehicle = await response.json();
        setVehicles([...vehicles, addedVehicle]);
        setNewVehicle({
          cabModel: "",
          licensePlate: "",
          year: "",
          make: "",
          color: "",
          engine: "",
          fuelType: "",
          seatCount: "", // Clear seatCount
          price: "",     // Clear price
        });
        setShowForm(false);
      } else {
        console.error("Failed to add vehicle");
      }
    } catch (error) {
      console.error("Error adding vehicle:", error);
    }
  };

  // Delete vehicle
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`${API_URL}?id=${id}`, {
        method: "DELETE",
      });
      if (response.ok) {
        setVehicles(vehicles.filter((vehicle) => vehicle.id !== id));
      } else {
        console.error("Failed to delete vehicle");
      }
    } catch (error) {
      console.error("Error deleting vehicle:", error);
    }
  };

  // Edit vehicle
  const handleEdit = (vehicle) => {
    setEditingVehicle({ ...vehicle }); // Ensure we don't mutate the original object
  };

  // Update vehicle
  const handleUpdate = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(API_URL, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(editingVehicle),
      });
      if (response.ok) {
        setVehicles(
          vehicles.map((vehicle) =>
            vehicle.id === editingVehicle.id ? editingVehicle : vehicle
          )
        );
        setEditingVehicle(null);
      } else {
        console.error("Failed to update vehicle");
      }
    } catch (error) {
      console.error("Error updating vehicle:", error);
    }
  };

  return (
    <div className="vehicle-container">
      <h2>Vehicle Management</h2>
      <button className="corner-button" onClick={() => setShowForm(!showForm)}>
        {showForm ? "Hide Form" : "Add Vehicle"}
      </button>

      {showForm && (
        <form className="rides-form classic-form" onSubmit={handleAddVehicle}>
          <h2 className="form-title">Cab Details</h2>
          {Object.keys(newVehicle).map((key) => (
            <div className="form-group" key={key}>
              <label htmlFor={key}>
                {key.charAt(0).toUpperCase() + key.slice(1)}
              </label>
              <input
                type="text"
                id={key}
                placeholder={`Enter ${key}`}
                value={newVehicle[key]}
                onChange={handleChange}
              />
            </div>
          ))}
          <button type="submit" className="save-button">
            Save Details
          </button>
        </form>
      )}

      <table className="vehicle-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Cab Model</th>
            <th>License Plate</th>
            <th>Year</th>
            <th>Make</th>
            <th>Color</th>
            <th>Engine</th>
            <th>Fuel Type</th>
            <th>Seat Count</th> {/* New column */}
            <th>Price</th> {/* New column */}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {vehicles.map((vehicle) => (
            <tr key={vehicle.id}>
              <td>{vehicle.id}</td>
              <td>{vehicle.cabModel}</td>
              <td>{vehicle.licensePlate}</td>
              <td>{vehicle.year}</td>
              <td>{vehicle.make}</td>
              <td>{vehicle.color}</td>
              <td>{vehicle.engine}</td>
              <td>{vehicle.fuelType}</td>
              <td>{vehicle.seatcount}</td> {/* Display seat count */}
              <td>{vehicle.price}</td> {/* Display price */}
              <td>
                <button className="edit-button" onClick={() => handleEdit(vehicle)}>
                  Edit
                </button>
                <button className="delete-button" onClick={() => handleDelete(vehicle.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingVehicle && (
        <div className="edit-form-container">
          <div className="edit-form">
            <button className="close-button" onClick={() => setEditingVehicle(null)}>✖</button>
            <h3>Edit Vehicle</h3>
            <form onSubmit={handleUpdate}>
              <div className="form-grid">
                {Object.keys(editingVehicle).map(
                  (key) =>
                    key !== "id" && (
                      <div className="form-group" key={key}>
                        <label htmlFor={key}>
                          {key.charAt(0).toUpperCase() + key.slice(1)}
                        </label>
                        <input
                          type="text"
                          id={key}
                          value={editingVehicle[key]}
                          onChange={(e) =>
                            setEditingVehicle({ ...editingVehicle, [key]: e.target.value })
                          }
                        />
                      </div>
                    )
                )}
              </div>
              <div className="form-actions">
                <button type="submit" className="save-button">Save</button>
                <button type="button" onClick={() => setEditingVehicle(null)} className="cancel-button">
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

export default Vehicle;
