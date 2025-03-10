package org.example.citycab.entities;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter

public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
        private String licenseNumber;
        private String phoneNumber;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    @JsonIgnore
    private Vehicle vehicle;

}
