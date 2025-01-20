package com.Project.RMSSpring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orderFood")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user; // The customer who places the order

    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private String status; // Status of the order (PENDING, APPROVED, REJECTED,  )

    @Column(nullable = false)
    private double totalPrice; // Total price of the order (food price * quantity)

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin; // Admin who approves the order

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private User staff; // Staff who serves the food

    private String notes;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Bill bill;

}
