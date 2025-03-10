import React, { useState, useEffect } from "react";
import "./user.css";

const User = () => {
  const [users, setUsers] = useState([]);
  const [editingUser, setEditingUser] = useState(null);
  const [newUser, setNewUser] = useState({
    username: "",
    password: "",
    role: "user",
  });
  const [showForm, setShowForm] = useState(false);

  // Fetch users from API
  useEffect(() => {
    fetch("http://localhost:8091/citycab_war_exploded/users")
      .then((response) => response.json())
      .then((data) => setUsers(data))
      .catch((error) => console.error("Error fetching users:", error));
  }, []);

  // Delete user
const handleDelete = (id) => {
  const confirmDelete = window.confirm("Are you sure you want to delete this user?");
  if (!confirmDelete) return;

  fetch(`http://localhost:8091/citycab_war_exploded/users?id=${id}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to delete user");
      }
      setUsers(users.filter((user) => user.id !== id));
    })
    .catch((error) => {
      console.error("Error deleting user:", error);
      alert("Failed to delete the user. Please try again.");
    });
};


  // Edit user
  const handleEdit = (user) => {
    setEditingUser(user);
  };

  // Update user details
  const handleUpdate = (event) => {
    event.preventDefault();

    fetch(`http://localhost:8091/citycab_war_exploded/users`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(editingUser),
    })
      .then((response) => response.json())
      .then((updatedUser) => {
        setUsers(users.map((user) => (user.id === updatedUser.id ? updatedUser : user)));
        setEditingUser(null);
      })
      .catch((error) => console.error("Error updating user:", error));
  };

  // Add new user
  const handleAddUser = (event) => {
    event.preventDefault();

    fetch("http://localhost:8091/citycab_war_exploded/users", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    })
      .then((response) => response.json())
      .then((addedUser) => {
        setUsers([...users, addedUser]);
        setNewUser({ username: "", password: "", role: "user" });
        setShowForm(false);
      })
      .catch((error) => console.error("Error adding user:", error));
  };

  return (
    <div className="user-container">
      <h2>User Management</h2>
      <button className="corner-button" onClick={() => setShowForm(!showForm)}>
        {showForm ? "Hide Form" : "Add User"}
      </button>

      {showForm && (
        <div className="user-form classic-form">
          <h2 className="form-title">User Details</h2>
          <form onSubmit={handleAddUser}>
            <div className="form-group">
              <label>Username</label>
              <input
                type="text"
                value={newUser.username}
                onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
                required
              />
            </div>
            
            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                value={newUser.password}
                onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Role</label>
              <select
                value={newUser.role}
                onChange={(e) => setNewUser({ ...newUser, role: e.target.value })}
              >
                <option value="user">User</option>
                <option value="admin">Admin</option>
              </select>
            </div>
            <button className="submit-button" type="submit">Save Details</button>
          </form>
        </div>
      )}

      <table className="user-table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.role}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(user)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(user.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingUser && (
        <div className="edit-form">
          <h3>Edit User</h3>
          <form onSubmit={handleUpdate}>
            <div className="form-group">
              <label>Username</label>
              <input
                type="text"
                value={editingUser.username}
                onChange={(e) => setEditingUser({ ...editingUser, username: e.target.value })}
                required
              />
            </div>
         
            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                value={editingUser.password}
                onChange={(e) => setEditingUser({ ...editingUser, password: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Role</label>
              <select
                value={editingUser.role}
                onChange={(e) => setEditingUser({ ...editingUser, role: e.target.value })}
              >
                <option value="user">User</option>
                <option value="admin">Admin</option>
              </select>
            </div>
            <div className="form-actions">
              <button type="submit">Update</button>
              <button type="button" onClick={() => setEditingUser(null)}>Cancel</button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default User;
