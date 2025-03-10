import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Navbar from './Cab/navbar';
import Home from './Cab/home';
import About from './Cab/about';

import Contact from './Cab/contact';
import Login from './Cab/login';
import Register from './Cab/register';
import Footer from './Cab/footer';
import Rides from './Cab/rides';




const org = () => (
  <Router>
    <Navbar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/about" element={<About />} />
      <Route path="/rides" element={<Rides />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/contact" element={<Contact />} />
      
      
    </Routes>
    <Footer/>
  </Router>
);

export default org;
