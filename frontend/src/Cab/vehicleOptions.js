import React, { useState, useEffect } from "react";
import economyImage from "../assets/economy.png";
import standardImage from "../assets/standerd.png";
import firstClassImage from "../assets/first.png";
import suvImage from "../assets/passanger1.png";
import vanStandardImage from "../assets/standerd van1.png";
import vanFirstClassImage from "../assets/1stclass.png";
import minibusImage from "../assets/minibus.png";
import "./vehicle.css";


const imageMap = {
  "Economy": economyImage,
  "Standard Class": standardImage,
  "First Class": firstClassImage,
  "SUV": suvImage,
  "Van Standard": vanStandardImage,
  "Van First Class": vanFirstClassImage,
  "Minibus": minibusImage,
};

const VehicleOptions = ({ passengers, selectedVehicle, setSelectedVehicle }) => {
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchVehicles = async () => {
      try {
        const response = await fetch("http://localhost:8091/citycab_war_exploded/vehicle");
        if (!response.ok) {
          throw new Error("Failed to fetch vehicle data");
        }
        const data = await response.json();
        console.log("Vehicle data:", data);

        const mappedVehicles = data.map((vehicle) => ({
          id: vehicle.id,
          name: vehicle.cabModel,
          capacity: vehicle.seatcount,
          image: imageMap[vehicle.cabModel] || null,
        }));

        setVehicles(mappedVehicles);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchVehicles();
  }, []);

  const filteredVehicles = vehicles.filter((vehicle) => vehicle.capacity >= passengers);

  const handleVehicleClick = (vehicle) => {
    setSelectedVehicle(vehicle);
    console.log("Selected vehicle:", vehicle);
  };

  if (loading) {
    return <div>Loading vehicles...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="vehicle-options-container">
      <div className="vehicle-selection classic-vehicle-section">
        <h2>Recommended Vehicles</h2>
        <div className="vehicle-list">
          {filteredVehicles.map((vehicle) => (
            <div
              className={`vehicle-card classic-vehicle-card ${
                selectedVehicle && selectedVehicle.id === vehicle.id ? "blink" : ""
              }`}
              key={vehicle.id}
            >
              <label className="vehicle-label">
                <input
                  type="radio"
                  name="vehicle-selection"
                  value={vehicle.id}
                  checked={selectedVehicle && selectedVehicle.id === vehicle.id}
                  onChange={() => handleVehicleClick(vehicle)}
                  className="vehicle-radio"
                />
                <div className="vehicle-content" onClick={() => handleVehicleClick(vehicle)}>
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
              </label>
            </div>
          ))}
        </div>
      </div>

      <br />

      <div className="vehicle-options-header">
        <h2>Maximum comfort and safety for your trip</h2>
        <p>⭐ Licensed vehicles, professional drivers</p>
      </div>
    </div>
  );
};

export default VehicleOptions;