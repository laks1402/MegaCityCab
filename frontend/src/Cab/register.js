import React, { useState } from 'react';
import { Link } from 'react-router-dom';  // Import Link from react-router-dom
import './login.css';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your registration logic here
    if (formData.password === formData.confirmPassword) {
      console.log('Registration successful', formData);
    } else {
      console.log('Passwords do not match');
    }
  };

  return (
    <div className="register-container">
      <div className="register-box">
        <h1 className="heading">Create Account</h1>
        <p className="subheading">Fill in the details to register</p>
      
        <form onSubmit={handleSubmit}>
          {/* Username Input */}
          <h1 className="heading2">Username</h1>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            placeholder="Username"
            className="input-field"
          />

          {/* Email Input */}
          <h1 className="heading2">Email</h1>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Email Address"
            className="input-field"
          />

          {/* Password Input */}
          <h1 className="heading2">Password</h1>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Password"
            className="input-field"
          />

          {/* Re-enter Password Input */}
          <h1 className="heading2">Re-enter Password</h1>
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            placeholder="Re-enter Password"
            className="input-field"
          />

          {/* Register Button */}
          <button type="submit" className="button">
            Register
          </button>
          <br/>
          
          {/* Link to Login Page */}
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
