import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import logo from "../assets/—Pngtree—taxi_1806490.png";
import "./login.css";

// Set up axios interceptor to include token in all requests
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

function Login() {
  const navigate = useNavigate();
  
  const [credentials, setCredentials] = useState({ username: "", password: "" });
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    const { username, password } = credentials;

    if (!username || !password) {
      setErrorMessage("Both username and password are required");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8091/citycab_war_exploded/loginm",
        new URLSearchParams({
          username: credentials.username,
          password: credentials.password
        }), // Send data as form-urlencoded since backend expects parameters
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }
      );

      if (response.status === 200) {
        const { token, role, username } = response.data;
        
        // Store token and user info in localStorage
        localStorage.setItem("token", token);
        localStorage.setItem("role", role);
        localStorage.setItem("username", username);

        // Navigate based on role
        switch (role.toLowerCase()) {
          case "admin":
            navigate("/admin");
            break;
          case "user":
            navigate("/rides");
            break;
          default:
            navigate("/");
        }
      }
    } catch (error) {
      setErrorMessage(error.response?.data?.error || "Invalid credentials");
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <div className="mb-8 flex flex-col items-center">
          <img src={logo} className="logo" alt="Logo" />
          <span className="subtitle">Enter Login Details</span>
        </div>

        <form onSubmit={handleLogin}>
          <InputField
            label="User name / Email"
            type="text"
            name="username"
            placeholder="id@email.com"
            value={credentials.username}
            onChange={handleChange}
          />
          <InputField
            label="Password"
            type="password"
            name="password"
            placeholder="*********"
            value={credentials.password}
            onChange={handleChange}
          />

          {errorMessage && <div className="error-message">{errorMessage}</div>}

          <div className="flex flex-col space-y-4 items-center">
            <button type="submit" className="button">Login</button>
            <button 
              type="button" 
              className="button button-secondary" 
              onClick={() => navigate("/register")}
            >
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

const InputField = ({ label, type, name, placeholder, value, onChange }) => (
  <div className="input-field">
    <h1 className="heading2">{label}</h1>
    <input
      type={type}
      name={name}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      aria-label={label}
    />
  </div>
);

export default Login;