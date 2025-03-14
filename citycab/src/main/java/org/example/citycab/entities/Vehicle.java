package org.example.citycab.entities;


//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private int id;
    private String cabModel;
    private String licensePlate;
    private int year;
    private String make;
    private String color;
    private String engine;
    private String fuelType;
    private double price;
    private int seatcount;
    private String image;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private  Driver driver;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> booking = new ArrayList<>();
}
