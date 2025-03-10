// VehicleOptions.js
import React, { useState } from "react";

// Import images from src/assets
import economyImage from "../assets/economy.png";
import standardImage from "../assets/standerd.png";
import firstClassImage from "../assets/first.png";
import suvImage from "../assets/passanger1.png";
import vanStandardImage from "../assets/standerd van1.png";
import vanFirstClassImage from "../assets/1stclass.png";
import minibusImage from "../assets/minibus.png";

// Vehicle data with imported images
const vehicleData = [
  { name: "Economy Taxi", capacity: 3, image: economyImage },
  { name: "Standard Class", capacity: 3, image: standardImage },
  { name: "First Class", capacity: 3, image: firstClassImage },
  { name: "SUV", capacity: 6, image: suvImage },
  { name: "Van Standard", capacity: 7, image: vanStandardImage },
  { name: "Van First Class", capacity: 12, image: vanFirstClassImage },
  { name: "Minibus", capacity: 16, image: minibusImage },
];

const VehicleOptions = ({ passengers }) => {
  const [selectedVehicle, setSelectedVehicle] = useState(null);

  // Filter vehicles based on the passengers count
  const filteredVehicles = vehicleData.filter((vehicle) => vehicle.capacity >= passengers);

  const handleVehicleClick = (vehicleName) => {
    setSelectedVehicle(vehicleName);
  };

  return (
    <div className="vehicle-options-container">
      {/* Recommended Vehicles Section */}
      <div className="vehicle-selection classic-vehicle-section">
        <h2>Recommended Vehicles</h2>
        <div className="vehicle-list">
          {filteredVehicles.map((vehicle, index) => (
            <div
              className={`vehicle-card classic-vehicle-card ${
                selectedVehicle === vehicle.name ? "blink" : ""
              }`}
              key={index}
              onClick={() => handleVehicleClick(vehicle.name)}
            >
              <img
                src={vehicle.image}
                alt={vehicle.name}
                className="vehicle-image"
              />
              <div className="vehicle-info">
                <p className="vehicle-name">{vehicle.name}</p>
                <p className="vehicle-capacity">
                  Up to {vehicle.capacity} Passengers
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>

      <br />

      {/* Header Section */}
      <div className="vehicle-options-header">
        <h2>Maximum comfort and safety for your trip</h2>
        <p>⭐ Licensed vehicles, professional drivers</p>
      </div>
    </div>
  );
};

export default VehicleOptions;
