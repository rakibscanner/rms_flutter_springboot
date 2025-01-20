package com.Project.RMSSpring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private LocalDateTime billDate;

    @Column(length = 20)
    private String paymentMethod; // Cash, Card

    @Column(nullable = false)
    private String status;  // PAID, UNPAID, CANCELLED

    // User Relationship (Bill belongs to a user who places an order)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User paidBy;

    // Admin Relationship (Admin who approves the bill)
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User receivedBy;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
