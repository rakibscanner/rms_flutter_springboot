package com.Project.RMSSpring.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false, length=50)
    private String tableNumber;

    @Column(nullable=false, length=50)
    private int capacity;

    @Column(nullable=false, length=50)
    private String status;

    // Relation with TableBooking
    @JsonIgnore
    @OneToMany(mappedBy = "tables", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TableBooking> bookings;
}
