package com.Project.RMSSpring.repository;

import com.Project.RMSSpring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by user ID (for customer-specific orders)
    List<Order> findByUserId(Long userId);

    // Find orders by status (for admin to see pending or approved orders)
    List<Order> findByStatus(String status);

    Optional<Order> findByBill_Id(Long billId);

    Optional<List<Order>> findAllByUser_Id(Long userId);
}
