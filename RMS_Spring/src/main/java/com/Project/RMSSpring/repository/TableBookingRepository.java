package com.Project.RMSSpring.repository;


import com.Project.RMSSpring.entity.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
    List<TableBooking> findByStatus(String status); // To find pending bookings
    List<TableBooking> findByBookedById(Long userId);
}
