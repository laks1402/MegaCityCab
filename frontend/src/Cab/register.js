import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Added useNavigate for redirection
import './login.css';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    address: '',
    identityCard: '',
    phoneNumber: '',
  });

  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false); // Track if message is an error
  const navigate = useNavigate(); // For redirecting after success

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Client-side validation
    if (formData.password !== formData.confirmPassword) {
      setMessage("Passwords do not match!");
      setIsError(true);
      return;
    }

    if (!formData.username || !formData.email || !formData.password || !formData.identityCard || !formData.phoneNumber) {
      setMessage("Please fill in all required fields!");
      setIsError(true);
      return;
    }

    const userData = {
      username: formData.username,
      email: formData.email,
      password: formData.password,
      address: formData.address,
      identityCard: formData.identityCard,
      phoneNumber: formData.phoneNumber,
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
        setMessage("Registration successful! Redirecting to login...");
        setIsError(false);
        setTimeout(() => navigate('/login'), 2000); // Redirect after 2 seconds
      } else {
        const errorData = await response.json();
        setMessage(errorData.error || "Registration failed!");
        setIsError(true);
      }
    } catch (error) {
      setMessage("Server error. Please try again later.");
      setIsError(true);
      console.error("Registration error:", error);
    }
  };

  return (
    <div className="register-container">
      <div className="register-box">
        <h1 className="heading">Create Account</h1>
        <p className="subheading">Fill in the details to register</p>

        <form onSubmit={handleSubmit} className="register-form">
          <div className="form-group">
            <h2 className="heading2">Username</h2>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              placeholder="Username"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Email</h2>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Email Address"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Password</h2>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Password"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Re-enter Password</h2>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              placeholder="Re-enter Password"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Address</h2>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              placeholder="Address"
              className="input-field"
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Identity Card</h2>
            <input
              type="text"
              name="identityCard"
              value={formData.identityCard}
              onChange={handleChange}
              placeholder="Identity Card Number"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <h2 className="heading2">Phone Number</h2>
            <input
              type="tel"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              placeholder="Phone Number"
              className="input-field"
              required
            />
          </div>

          {message && (
            <p className={`message ${isError ? 'error' : 'success'}`}>
              {message}
            </p>
          )}

          <div className="button-group">
            <button type="submit" className="button">
              Register
            </button>
            <Link to="/login">
              <button type="button" className="button">
                Login
              </button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;