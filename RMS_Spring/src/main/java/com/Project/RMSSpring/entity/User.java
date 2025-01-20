package com.Project.RMSSpring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, unique = true)

    private String password;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    private String image;

//    @Column(nullable = false)
    private boolean active;

//    @Column(nullable = false)
//    private boolean lock;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    @Override
//    public boolean isAccountNonLocked() {
//        return lock;
//    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

    // Relation with TableBooking
    @JsonIgnore
    @OneToMany(mappedBy = "bookedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TableBooking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL)
    private List<Bill> billsAsUser;

    @JsonIgnore
    @OneToMany(mappedBy = "receivedBy", cascade = CascadeType.ALL)
    private List<Bill> billsAsAdmin;
}
