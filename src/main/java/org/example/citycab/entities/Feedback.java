package org.example.citycab.entities;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int feedbackId;
    private Date date;
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
