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

public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String taxName;
    private double taxRate;

    @OneToMany(mappedBy = "tax",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> booking = new ArrayList<>();

}
