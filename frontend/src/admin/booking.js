import React, { useState, useEffect } from "react";
import "./booking.css";

const Booking = () => {
  const [bookings, setBookings] = useState([]);
  const [editingStatus, setEditingStatus] = useState(null);

  // Fetch bookings from the API
  useEffect(() => {
    fetch("http://localhost:8091/citycab_war_exploded/booking")
      .then((response) => response.json())
      .then((data) => {
        const formattedBookings = data.map((booking) => ({
          id: booking.id,
          fromLocation: booking.fromLocation,
          toLocation: booking.toLocation,
          time: booking.time,
          date: new Date(booking.date).toLocaleDateString(),
          customer: booking.customer?.name,
          status: booking.payment ? "Confirmed" : "Pending",
          tax_id: booking.tax_id,
          vehicle_id: booking.vehicle_id,
        }));
        setBookings(formattedBookings);
      })
      .catch((error) => console.error("Error fetching bookings:", error));
  }, []);

  const handleDelete = (id) => {
    console.log("Deleting booking with id:", id);
    if (!id) {
      alert("Booking ID is required!");
      return;
    }

    const confirmDelete = window.confirm("Are you sure you want to delete this booking?");
    if (!confirmDelete) return;

    const updatedBookings = bookings.filter((booking) => booking.id !== id);
    setBookings(updatedBookings);

    fetch(`http://localhost:8091/citycab_war_exploded/booking?id=${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to delete booking");
        }
      })
      .catch((error) => {
        console.error("Error deleting booking:", error);
        setBookings(bookings);
        alert("Failed to delete the booking. Please try again.");
      });
  };

  // Update status function
  const handleStatusUpdate = (event) => {
    event.preventDefault();
    const updatedBooking = editingStatus;

    fetch(`http://localhost:8091/citycab_war_exploded/booking/updateStatus/${updatedBooking.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ status: updatedBooking.status }),
    })
      .then((response) => response.json())
      .then(() => {
        setBookings(
          bookings.map((booking) =>
            booking.id === updatedBooking.id ? { ...booking, status: updatedBooking.status } : booking
          )
        );
        setEditingStatus(null);
      })
      .catch((error) => console.error("Error updating status:", error));
  };

  return (
    <div className="booking-container">
      <h2>Booking List</h2>
      <table className="booking-table">
        <thead>
          <tr>
            <th>id</th>
            <th>From Location</th>
            <th>To Location</th>
            <th>Time</th>
            <th>Date</th>
            <th>Customer</th>
            <th>Tax ID</th>
            <th>Vehicle ID</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {bookings.map((booking) => (
            <tr key={booking.id}>
              <td>{booking.id}</td>
              <td>{booking.fromLocation}</td>
              <td>{booking.toLocation}</td>
              <td>{booking.time}</td>
              <td>{booking.date}</td>
              <td>{booking.customer}</td>
              <td>{booking.tax_id}</td>
              <td>{booking.vehicle_id}</td>
              <td className={`status ${booking.status.toLowerCase()}`}>{booking.status}</td>
              <td>
                <button className="status-btn1" onClick={() => setEditingStatus(booking)}>E/Status</button>
                <button className="delete-btn" onClick={() => handleDelete(booking.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Edit Status Modal */}
      {editingStatus && (
        <div className="edit-form">
          <h3>Edit Booking Status</h3>
          <form onSubmit={handleStatusUpdate}>
            <label>Status:</label>
            <select
              value={editingStatus.status}
              onChange={(e) =>
                setEditingStatus({ ...editingStatus, status: e.target.value })
              }
            >
              <option value="Confirmed">Confirmed</option>
              <option value="Pending">Pending</option>
              <option value="Cancelled">Cancelled</option>
            </select>

            <button type="submit">Update</button>
            <button type="button" onClick={() => setEditingStatus(null)}>Cancel</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Booking;
