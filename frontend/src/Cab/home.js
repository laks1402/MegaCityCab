import React from "react";
import { useNavigate } from "react-router-dom";
import homeBg from "../assets/home2.jpg"; // Import the image
import'./home.css';

const Home = () => {
  const navigate = useNavigate(); // Hook for navigation

  return (
    <div>
      {/* Hero Section */}
      <div
        className="relative h-[85vh] flex flex-col justify-center items-center text-white bg-cover bg-center"
        style={{ backgroundImage: `url(${homeBg})` }}
      >
        <div className="absolute inset-0 bg-black opacity-50"></div>
        <div className="relative z-10 text-center max-w-3xl">
          <h1 className="text-5xl font-bold">Book Your Ride in Seconds!</h1>
          <p className="mt-4 text-lg">
            Safe, Affordable & Fast Cab Services Available 24/7.
          </p>
          <button
            className="mt-6 bg-yellow-500 px-6 py-3 text-black font-semibold rounded-md hover:bg-yellow-600"
            onClick={() => navigate("/rides")} // Navigate to Rides page
          >
            Book a Ride
          </button>
        </div>
      </div>

      {/* About Us Section */}
      <section className="py-16 bg-gray-100">
        <div className="max-w-5xl mx-auto text-center">
          <h2 className="text-3xl font-bold">About MegaCityCab</h2>
          <p className="mt-4 text-lg">
            MegaCityCab provides fast, affordable, and safe transportation with an easy-to-use booking platform. Whether you're commuting or heading out on a trip, our cabs are always available for you.
          </p>
        </div>
      </section>

      {/* Services Section */}
      <section className="py-16">
        <div className="max-w-5xl mx-auto text-center">
          <h2 className="text-3xl font-bold">Our Services</h2>
          <div className="mt-8 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            <div className="bg-white shadow-lg rounded-lg p-6">
              <h3 className="text-xl font-semibold">Quick Booking</h3>
              <p className="mt-2">Book your ride in just a few taps, anytime and anywhere.</p>
            </div>
            <div className="bg-white shadow-lg rounded-lg p-6">
              <h3 className="text-xl font-semibold">24/7 Availability</h3>
              <p className="mt-2">Our services are available around the clock for your convenience.</p>
            </div>
            <div className="bg-white shadow-lg rounded-lg p-6">
              <h3 className="text-xl font-semibold">Safe and Secure</h3>
              <p className="mt-2">Our drivers are professional and our vehicles are safe and well-maintained.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Contact Section */}
      <section className="py-16 bg-gray-100">
        <div className="max-w-5xl mx-auto text-center">
          <h2 className="text-3xl font-bold">Contact Us</h2>
          <p className="mt-4 text-lg">Have any questions? Reach out to us anytime.</p>
          <button
            className="mt-6 bg-yellow-500 px-6 py-3 text-black font-semibold rounded-md hover:bg-yellow-600"
            onClick={() => navigate("/contact")} // Navigate to Contact page
          >
            Contact Us
          </button>
        </div>
      </section>
    </div>
  );
};

export default Home;
