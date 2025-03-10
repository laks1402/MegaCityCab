import React, { useState } from 'react';
import contactImage from '../assets/contact.jpg'; // Import your local image

const ContactSection = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    message: '',
    agreement: false
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you can handle the form submission, maybe using an API call
    console.log(formData);
  };

  return (
    <section className>
      <div className="max-w-5xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden">
        <div className="relative h-80">
          <img 
            className="absolute h-full w-full object-cover" 
            src={contactImage} // Use the local image
            alt="Contact Background" 
          />
          <div className="absolute inset-0 bg-black bg-opacity-50 flex flex-col justify-center items-center text-white">
          <h2 className="text-4xl font-serif font-bold text-white">Contact Information</h2>
          <p className="mt-2 text-lg text-white text-center max-w-2xl">
                      Reach out to us for any inquiries or support. We are happy to assist you.
          </p>

          </div>
        </div>
        
        <div className="px-8 py-12">
          <div className="grid md:grid-cols-2 gap-8">
            {/* Contact Details */}
            <div className="space-y-6">
              <div className="flex items-center space-x-4">
                <span className="text-gray-600 text-xl">📍</span>
                <p className="text-lg font-semibold">3/6 vellawathe Colombo</p>
              </div>
              <div className="flex items-center space-x-4">
                <span className="text-gray-600 text-xl">📞</span>
                <p className="text-lg font-semibold">+94 11 2525475</p>
              </div>
              <div className="flex items-center space-x-4">
                <span className="text-gray-600 text-xl">📧</span>
                <p className="text-lg font-semibold">office@megacitycab.com</p>
              </div>
            </div>
            
            {/* Contact Form */}
            <form onSubmit={handleSubmit} className="bg-gray-50 p-6 rounded-lg shadow-md">
              <div className="mb-4">
                <label className="block text-gray-700 font-medium">Full Name</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  placeholder="Enter your name"
                  className="w-full p-3 border rounded-md focus:ring-2 focus:ring-gray-400"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 font-medium">Email Address</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  placeholder="Enter your email"
                  className="w-full p-3 border rounded-md focus:ring-2 focus:ring-gray-400"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 font-medium">Phone Number</label>
                <input
                  type="tel"
                  name="phone"
                  value={formData.phone}
                  onChange={handleChange}
                  placeholder="Enter your phone number"
                  className="w-full p-3 border rounded-md focus:ring-2 focus:ring-gray-400"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 font-medium">Message</label>
                <textarea
                  name="message"
                  value={formData.message}
                  onChange={handleChange}
                  placeholder="Your message here..."
                  rows="4"
                  className="w-full p-3 border rounded-md focus:ring-2 focus:ring-gray-400"
                  required
                ></textarea>
              </div>
              <div className="mb-4 flex items-center">
                <input
                  type="checkbox"
                  name="agreement"
                  checked={formData.agreement}
                  onChange={handleChange}
                  className="mr-2"
                />
                <span className="text-gray-700">I agree to the <a href="#" className="text-blue-600">terms and conditions</a></span>
              </div>
              <button type="submit" className="w-full bg-gray-800 text-white py-3 rounded-md text-lg font-medium hover:bg-gray-900">
                Send Message
              </button>
            </form>
          </div>
        </div>
       
      </div>
    </section>
  );
};

export default ContactSection;
