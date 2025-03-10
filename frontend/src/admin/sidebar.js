import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom"; // Add useNavigate
import {
  FaTachometerAlt,
  FaUser,
  FaCar,
  FaUserTie,
  FaCommentDots,
  FaSignOutAlt,
  FaClipboardList,
  FaBars,
} from "react-icons/fa";

const Sidebar = () => {
  const [isOpen, setIsOpen] = useState(true);
  const navigate = useNavigate(); // Initialize useNavigate

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  // Modified handleLogout function
  const handleLogout = () => {
    console.log("Logging out...");
    // Add your logout logic here (e.g., clear auth tokens)
    localStorage.clear(); // Example: Clear local storage
    // Redirect to login page
    navigate("/login");
  };

  return (
    <aside
      className={`bg-gradient-to-br from-gray-800 to-gray-900 fixed h-full w-72 text-white ${
        isOpen ? "block" : "hidden"
      } md:block`}
    >
      {/* Sidebar Header */}
      <div className="border-b border-white/20 p-6 flex justify-between items-center">
        <h6 className="text-lg font-semibold">Admin Dashboard</h6>
        <button
          className="text-white md:hidden"
          onClick={toggleSidebar}
        >
          <FaBars className="w-6 h-6" />
        </button>
      </div>

      {/* Sidebar Navigation */}
      <ul className="mt-4">
        {/* Dashboard */}
        <li>
          <NavLink
            to="/Admin"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs font-bold rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaTachometerAlt className="w-5 h-5" />
            Dashboard
          </NavLink>
        </li>

        {/* Booking */}
        <li>
          <NavLink
            to="/Admin/booking"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaClipboardList className="w-5 h-5" />
            Booking
          </NavLink>
        </li>

        {/* Customer Management */}
        <li>
          <NavLink
            to="/Admin/customer"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaUser className="w-5 h-5" />
            Customer Management
          </NavLink>
        </li>

        {/* Vehicle Management */}
        <li>
          <NavLink
            to="/Admin/vehicle"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaCar className="w-5 h-5" />
            Vehicle Management
          </NavLink>
        </li>

        {/* Driver Management */}
        <li>
          <NavLink
            to="/Admin/driver"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaUserTie className="w-5 h-5" />
            Driver Management
          </NavLink>
        </li>

        {/* Feedback */}
        <li>
          <NavLink
            to="/Admin/feedback"
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg ${
                isActive ? "bg-yellow-500 shadow-md" : "hover:bg-white/10"
              }`
            }
          >
            <FaCommentDots className="w-5 h-5" />
            Feedback
          </NavLink>
        </li>

        {/* Logout Button */}
        <li>
          <button
            onClick={handleLogout}
            className="flex items-center gap-4 px-4 py-3 w-full text-xs rounded-lg text-left hover:bg-white/10"
          >
            <FaSignOutAlt className="w-5 h-5" />
            Sign Out
          </button>
        </li>
      </ul>
    </aside>
  );
};

export default Sidebar;