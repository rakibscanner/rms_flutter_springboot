package com.Project.RMSSpring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 50, unique = true)
    private String email;

    @Column(nullable = false,length = 50, unique = true)
    private String phone;

    @Column(nullable = false,length = 50)
    private String address;

    @Column(nullable = false)
    private String image;

}
