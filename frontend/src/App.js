import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

// Admin Components
import Sidebar from "./admin/sidebar";
import Dashboard from "./admin/dashboard";
import Driver from "./admin/driver";
import Feedback from "./admin/feedback";
import User from "./admin/customer";
// import Notification from "./admin/notification";
import Booking from "./admin/booking";
import Vehicle from "./admin/vehicle";

// Cab Components
import Navbar from './Cab/navbar';
import Home from './Cab/home';
import About from './Cab/about';

import Contact from './Cab/contact';
import Login from './Cab/login';
import Register from './Cab/register';
import Footer from './Cab/footer';
import Rides from './Cab/rides';




const App = () => (
  <Router>
    <Routes>
      {/* Admin Routes */}
      <Route
        path="/admin/*"
        element={
          <div className="flex">
            <Sidebar />
            <div className="flex-1 p-4 ml-72">
              <Routes>
                <Route path="/" element={<Dashboard />} />
                <Route path="/driver" element={<Driver />} />
                <Route path="/booking" element={<Booking />} />
                <Route path="/customer" element={<User />} />
                <Route path="/vehicle" element={<Vehicle />} />
                <Route path="/feedback" element={<Feedback />} />
              </Routes>
            </div>
          </div>
        }
      />

      {/* Cab Routes */}
      <Route
        path="/*"
        element={
          <>
            <Navbar />
            <div style={{paddingTop:70}}>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/about" element={<About />} />
              <Route path="/rides" element={<Rides />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/contact" element={<Contact />} />
            </Routes>
            </div>
            <Footer />
          </>
        }
      />
    </Routes>
  </Router>
);

export default App;
