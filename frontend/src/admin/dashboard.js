import React from "react";

const Dashboard = () => {
  return (
    <div className="min-h-screen bg-gray-100 p-6">
      {/* Breadcrumb */}
      <div className="text-gray-600 text-sm mb-6">
        Dashboard / <span className="text-black font-semibold">Cab Booking Admin</span>
      </div>

      {/* Heading */}
      <h1 className="text-2xl font-semibold text-gray-800 mb-6">Admin Dashboard</h1>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
        {/* Total Bookings */}
        <div className="bg-white p-4 rounded-lg shadow-md flex items-center">
          <div className="w-12 h-12 bg-blue-100 text-blue-600 rounded-full flex items-center justify-center mr-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={2}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M3 10h11M9 21V3m0 0L3 9m6-6l6 6"
              />
            </svg>
          </div>
          <div>
            <h2 className="text-gray-600 text-sm">Total Bookings</h2>
            <p className="text-xl font-bold">5,342</p>
            <p className="text-sm text-green-500">+12% than last week</p>
          </div>
        </div>

        {/* Active Drivers */}
        <div className="bg-white p-4 rounded-lg shadow-md flex items-center">
          <div className="w-12 h-12 bg-green-100 text-green-600 rounded-full flex items-center justify-center mr-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={2}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M16 8a6 6 0 11-12 0 6 6 0 0112 0zM8 14a8 8 0 018 8H0a8 8 0 018-8z"
              />
            </svg>
          </div>
          <div>
            <h2 className="text-gray-600 text-sm">Active Drivers</h2>
            <p className="text-xl font-bold">45</p>
            <p className="text-sm text-green-500">+8% than last month</p>
          </div>
        </div>

        {/* Total Revenue */}
        <div className="bg-white p-4 rounded-lg shadow-md flex items-center">
          <div className="w-12 h-12 bg-orange-100 text-orange-600 rounded-full flex items-center justify-center mr-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={2}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M4 16V4m8 0v16m4-10l4 6M2 2l20 20"
              />
            </svg>
          </div>
          <div>
            <h2 className="text-gray-600 text-sm">Total Revenue</h2>
            <p className="text-xl font-bold">$128,540</p>
            <p className="text-sm text-green-500">+15% than last month</p>
          </div>
        </div>

        {/* Trips in Progress */}
        <div className="bg-white p-4 rounded-lg shadow-md flex items-center">
          <div className="w-12 h-12 bg-yellow-100 text-yellow-600 rounded-full flex items-center justify-center mr-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={2}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M12 6v12m6-6H6"
              />
            </svg>
          </div>
          <div>
            <h2 className="text-gray-600 text-sm">Trips in Progress</h2>
            <p className="text-xl font-bold">73</p>
            <p className="text-sm text-red-500">-5% than last week</p>
          </div>
        </div>
      </div>

      {/* Pending Requests */}
      <div className="bg-white p-6 rounded-lg shadow-md mb-6">
        <h2 className="text-lg font-semibold text-gray-800 mb-4">Pending Requests</h2>
        <p className="text-sm text-gray-500 mb-4">
          <span className="text-red-500 font-bold">✔ 25 pending</span> booking requests
        </p>
        <table className="w-full table-auto text-left">
          <thead>
            <tr>
              <th className="text-gray-600 text-sm font-semibold">Customer Name</th>
              <th className="text-gray-600 text-sm font-semibold">Pickup Location</th>
              <th className="text-gray-600 text-sm font-semibold">Dropoff Location</th>
              <th className="text-gray-600 text-sm font-semibold">Request Time</th>
            </tr>
          </thead>
          <tbody>
            <tr className="border-b">
              <td className="py-2 text-gray-700">John Doe</td>
              <td className="py-2 text-gray-700">123 Elm St.</td>
              <td className="py-2 text-gray-700">456 Oak St.</td>
              <td className="py-2 text-gray-700">10:30 AM</td>
            </tr>
            <tr className="border-b">
              <td className="py-2 text-gray-700">Jane Smith</td>
              <td className="py-2 text-gray-700">789 Pine St.</td>
              <td className="py-2 text-gray-700">101 Maple Ave.</td>
              <td className="py-2 text-gray-700">11:00 AM</td>
            </tr>
            <tr className="border-b">
              <td className="py-2 text-gray-700">Michael Johnson</td>
              <td className="py-2 text-gray-700">101 Cedar Rd.</td>
              <td className="py-2 text-gray-700">102 Birch Blvd.</td>
              <td className="py-2 text-gray-700">11:30 AM</td>
            </tr>
          </tbody>
        </table>
      </div>

      {/* Driver Performance Table */}
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold text-gray-800 mb-4">Driver Performance</h2>
        <table className="w-full table-auto text-left">
          <thead>
            <tr>
              <th className="text-gray-600 text-sm font-semibold">Driver Name</th>
              <th className="text-gray-600 text-sm font-semibold">Completed Rides</th>
              <th className="text-gray-600 text-sm font-semibold">Rating</th>
              <th className="text-gray-600 text-sm font-semibold">Earnings</th>
            </tr>
          </thead>
          <tbody>
            <tr className="border-b">
              <td className="py-2 text-gray-700">John Doe</td>
              <td className="py-2 text-gray-700">120</td>
              <td className="py-2 text-gray-700">4.9</td>
              <td className="py-2 text-gray-700">$3,500</td>
            </tr>
            <tr className="border-b">
              <td className="py-2 text-gray-700">Jane Smith</td>
              <td className="py-2 text-gray-700">90</td>
              <td className="py-2 text-gray-700">4.7</td>
              <td className="py-2 text-gray-700">$2,800</td>
            </tr>
            <tr className="border-b">
              <td className="py-2 text-gray-700">Michael Johnson</td>
              <td className="py-2 text-gray-700">150</td>
              <td className="py-2 text-gray-700">5.0</td>
              <td className="py-2 text-gray-700">$4,200</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Dashboard;
