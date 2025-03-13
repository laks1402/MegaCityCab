import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './login.css';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      setMessage("Passwords do not match!");
      return;
    }

    const userData = {
      username: formData.username,
      email: formData.email,
      password: formData.password,
    };

    try {
      const response = await fetch('http://localhost:8091/citycab_war_exploded/users', {  
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (response.ok) {
        setMessage("Registration successful!");
      } else {
        const errorData = await response.json();
        setMessage(errorData.error || "Registration failed!");
      }
    } catch (error) {
      setMessage("Server error. Please try again.");
    }
  };

  return (
    <div className="register-container">
      <div className="register-box">
        <h1 className="heading">Create Account</h1>
        <p className="subheading">Fill in the details to register</p>
      
        <form onSubmit={handleSubmit}>
          <h1 className="heading2">Username</h1>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            placeholder="Username"
            className="input-field"
          />

          <h1 className="heading2">Email</h1>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Email Address"
            className="input-field"
          />

          <h1 className="heading2">Password</h1>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Password"
            className="input-field"
          />

          <h1 className="heading2">Re-enter Password</h1>
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            placeholder="Re-enter Password"
            className="input-field"
          />

          {message && <p className="message">{message}</p>}

          <button type="submit" className="button">
            Register
          </button>
          <br/>
          
          <Link to="/login">
            <button type="button" className="button">
              Login
            </button>
          </Link>
        </form>
      </div>
    </div>
  );
};

export default Register;
