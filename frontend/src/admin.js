import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Sidebar from "./admin/sidebar";
import Dashboard from "./admin/dashboard";
import Table from "./admin/table";
import User from "./admin/user";
import Notification from "./admin/notification";
import Booking from "./admin/booking";
import Vehicle from "./admin/vehicle";

const Apps = () => (
  <Router>
    <div className="flex">
      <Sidebar />
      <div className="flex-1 p-4 ml-72"> {/* Adjust for sidebar width */}
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/table" element={<Table />} />
          <Route path="/booking" element={<Booking/>} />
          <Route path="/user" element={<User />} />
          <Route path="/vehicle" element={<Vehicle />} />


          <Route path="/notification" element={<Notification />} />
        </Routes>
      </div>
    </div>
  </Router>
);

export default Apps;
