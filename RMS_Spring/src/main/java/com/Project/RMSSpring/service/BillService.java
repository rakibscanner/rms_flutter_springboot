package com.Project.RMSSpring.service;

import com.Project.RMSSpring.entity.Bill;
import com.Project.RMSSpring.entity.Order;
import com.Project.RMSSpring.entity.User;
import com.Project.RMSSpring.repository.BillRepository;
import com.Project.RMSSpring.repository.OrderRepository;
import com.Project.RMSSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a bill for an order
    public Bill createBill(Long orderId, Long adminId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!"APPROVED".equals(order.getStatus())) {
            throw new RuntimeException("Only approved orders can have bills generated.");
        }

        User admin  = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Create a new bill
        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setTotalAmount(order.getTotalPrice());
        bill.setBillDate(LocalDateTime.now());
        bill.setStatus("UNPAID");
        bill.setPaidBy(order.getUser());
        bill.setReceivedBy(admin);

        return billRepository.save(bill);
    }

    // Pay the bill (User pays the bill)
    public Bill payBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.getStatus().equals("UNPAID")) {
            bill.setStatus("GIVEN");
        }

        return billRepository.save(bill);
    }

    // Confirm the bill (Admin confirms the bill)
    public Bill confirmBill(Long billId, Long adminId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (bill.getStatus().equals("GIVEN")) {
            bill.setReceivedBy(admin);
            bill.setStatus("PAID");
        }

        return billRepository.save(bill);
    }

    // Fetch a bill by its ID
    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));
    }
}
