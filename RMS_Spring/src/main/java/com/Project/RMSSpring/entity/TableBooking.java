package com.Project.RMSSpring.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Table_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // PENDING, APPROVED, REJECTED

    private LocalDateTime approvalDate;

    private LocalDateTime bookingDate;

    // Relation with User (Customer who booked the table)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User bookedBy;

    // Relation with Tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private Tables tables;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")  // Admin who approved the booking
    private User approvedBy;

}
