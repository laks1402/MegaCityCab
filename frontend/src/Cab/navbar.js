import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';
import logo from '../assets/—Pngtree—taxi_1806490.png';

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <img src={logo} alt="Company Logo" className="navbar-logo" />
        <span 
      className="company-name" 
      style={{ fontFamily: 'Poppins, sans-serif', fontWeight: 'bold', fontSize: '1.5rem', color: '#fff' }}
    >
  MEGACITY CAB
</span>
      </div>

     
      <button className="menu-toggle" onClick={() => setIsOpen(!isOpen)}>
  {isOpen ? '✖' : '☰'}
</button>


      <div className={`navbar-links ${isOpen ? 'open' : ''}`}>
        <Link to="/" className="nav-item" onClick={() => setIsOpen(false)}>Home</Link>
        <Link to="/about" className="nav-item" onClick={() => setIsOpen(false)}>About Us</Link>
        <Link to="/contact" className="nav-item" onClick={() => setIsOpen(false)}>Contact Us</Link>
        <Link to="/login" className="nav-item" onClick={() => setIsOpen(false)}>Login</Link>
      </div>
    </nav>
  );
};

export default Navbar;
