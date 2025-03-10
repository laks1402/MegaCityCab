import React from 'react';
import aboutImage from '../assets/megaCityCabImage1.jpg';  // Replace with an image related to MegaCityCab

const About = () => {
  return (
    <div className="py-16 bg-gray-100">
      <div className="container m-auto px-6 text-gray-600 md:px-12 xl:px-6">
        <div className="space-y-6 md:space-y-0 md:flex md:gap-6 lg:items-center lg:gap-12">
          <div className="md:5/12 lg:w-5/12">
            <img
              src={aboutImage}  // Use the imported image here
              alt="MegaCityCab - Ride with Comfort"
              loading="lazy"
              width="100%"  // Optional, adjust as needed
              height="auto" // Optional, adjust as needed
              className="rounded-lg"  // This will round the corners of the image
            />
          </div>
          <div className="md:7/12 lg:w-6/12">
            <h2 className="text-2xl text-gray-900 font-bold md:text-4xl">
              Welcome to MegaCityCab: Your Reliable Ride in the City
            </h2>
            <p className="mt-6 text-gray-600">
              At MegaCityCab, we are committed to providing fast, safe, and reliable rides to all our customers. 
              Whether you need a quick trip across the city or a comfortable ride for a special occasion, 
              we’ve got you covered. Our fleet of well-maintained vehicles and professional drivers ensure a 
              smooth experience every time.
            </p>
            <p className="mt-4 text-gray-600">
              With our easy-to-use app, booking a ride is just a tap away. Enjoy competitive pricing, 24/7 
              availability, and a focus on customer satisfaction. Get ready to explore the city in comfort with MegaCityCab!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;
