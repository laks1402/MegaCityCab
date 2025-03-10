import React, { useState, useEffect, useRef } from "react";
import VehicleOptions from "./vehicleOptions";
import "./Rides.css";

const Rides = () => {
  const [pickupDate, setPickupDate] = useState("");
  const [pickupTime, setPickupTime] = useState("");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [passengers, setPassengers] = useState(1);
  const [selectedVehicle, setSelectedVehicle] = useState(null);
  const [bookingMessage, setBookingMessage] = useState("");
  const mapRef = useRef(null); // Ref for the map container

  const API_BASE_URL = "http://localhost:8091/citycab_war_exploded";
  const GOOGLE_MAPS_API_KEY = "AIzaSyAfnU5Kz7HncH4JH5HVX4ptJgHBMOgE4Xw";

  useEffect(() => {
    // Load Google Maps API script
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}&libraries=places,drawing`;
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);

    script.onload = () => {
      initializeMap();
    };

    return () => {
      document.head.removeChild(script);
    };
  }, []);

  const initializeMap = () => {
    if (!mapRef.current || !window.google) return;

    const map = new window.google.maps.Map(mapRef.current, {
      center: { lat: 6.9271, lng: 79.8612 }, // Default center (Colombo, Sri Lanka)
      zoom: 10,
    });

    const directionsService = new window.google.maps.DirectionsService();
    const directionsRenderer = new window.google.maps.DirectionsRenderer({
      map: map,
    });

    // Update map when from/to locations change
    if (from && to) {
      const geocoder = new window.google.maps.Geocoder();

      const getRoute = async () => {
        try {
          const fromCoords = await geocodeAddress(from, geocoder);
          const toCoords = await geocodeAddress(to, geocoder);

          const request = {
            origin: fromCoords,
            destination: toCoords,
            travelMode: window.google.maps.TravelMode.DRIVING,
          };

          directionsService.route(request, (result, status) => {
            if (status === window.google.maps.DirectionsStatus.OK) {
              directionsRenderer.setDirections(result);
            } else {
              console.error("Directions request failed: ", status);
            }
          });
        } catch (error) {
          console.error("Geocoding error: ", error);
        }
      };

      getRoute();
    }
  };

  const geocodeAddress = async (address, geocoder) => {
    return new Promise((resolve, reject) => {
      geocoder.geocode({ address: address }, (results, status) => {
        if (status === window.google.maps.GeocoderStatus.OK) {
          resolve(results[0].geometry.location);
        } else {
          reject(new Error("Geocode was not successful: " + status));
        }
      });
    });
  };

  const handleSearch = async () => {
    if (!from || !to || !pickupDate || !pickupTime) {
      setBookingMessage("Please fill in all required fields.");
      return;
    }

    try {
      const formattedTime = pickupTime.length === 5 ? `${pickupTime}:00` : pickupTime;

      const bookingData = {
        fromLocation: from,
        toLocation: to,
        time: formattedTime,
        date: pickupDate,
        customer: { id: 1 },
        vehicle: { id: 1 },
        tax: { id: 1 },
        payment: { id: 1 },
      };

      const response = await fetch(`${API_BASE_URL}/booking`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(bookingData),
      });

      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(errorData || "Booking failed");
      }

      const bookedData = await response.json();
      setBookingMessage(`Booking saved successfully! ID: ${bookedData.id}`);
    } catch (error) {
      setBookingMessage("Error saving booking: " + error.message);
    }
  };

  return (
    <div className="rides-container">
      <header className="rides-header">
        <h1>Mega City CAB</h1>
      </header>

      <div className="rides-content">
        {/* Booking Form */}
        <div className="rides-form">
          <h2 className="form-title">Book Your Ride</h2>
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="from">From</label>
              <input
                type="text"
                id="from"
                placeholder="Address, airport, hotel, ..."
                value={from}
                onChange={(e) => setFrom(e.target.value)}
              />
            </div>
            <div className="form-group">
              <label htmlFor="to">To</label>
              <input
                type="text"
                id="to"
                placeholder="Address, airport, hotel, ..."
                value={to}
                onChange={(e) => setTo(e.target.value)}
              />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label htmlFor="pickup-date">Pickup Date</label>
              <input
                type="date"
                id="pickup-date"
                value={pickupDate}
                onChange={(e) => setPickupDate(e.target.value)}
              />
            </div>
            <div className="form-group">
              <label htmlFor="pickup-time">Pickup Time</label>
              <input
                type="time"
                id="pickup-time"
                value={pickupTime}
                onChange={(e) => setPickupTime(e.target.value)}
              />
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="passengers">Passengers</label>
            <input
              type="number"
              id="passengers"
              min="1"
              value={passengers}
              onChange={(e) => setPassengers(parseInt(e.target.value) || 1)}
            />
          </div>

          <button className="search-button" onClick={handleSearch}>
            Save Booking
          </button>
          {bookingMessage && <p className="booking-message">{bookingMessage}</p>}
        </div>

        {/* Map Section */}
        <div className="map-container" ref={mapRef}>
          {/* Map will render here via Google Maps API */}
        </div>
      </div>

      {/* Vehicle Options Section */}
      <VehicleOptions passengers={passengers} />
    </div>
  );
};

export default Rides;