package com.Project.RMSSpring.service;


import com.Project.RMSSpring.entity.Role;
import com.Project.RMSSpring.entity.TableBooking;
import com.Project.RMSSpring.entity.Tables;
import com.Project.RMSSpring.entity.User;
import com.Project.RMSSpring.repository.TableBookingRepository;
import com.Project.RMSSpring.repository.TableRepository;
import com.Project.RMSSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TableBookingService {

    @Autowired
    private TableBookingRepository tableBookingRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    // Retrieve all table bookings
    public List<TableBooking> getAllBookings() {
        try {
            return tableBookingRepository.findAll();
        }
        catch (Exception e) {
            System.out.println("Error in getAllBookings"+e.getMessage());
            throw new RuntimeException("Error in getAllBookings"+e.getMessage());
        }
    }

    // Retrieve a table booking by ID
    public TableBooking getBookingById(Long id) {
        return tableBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table booking not found: " + id));
    }

    // Retrieve bookings by user ID
    public List<TableBooking> getBookingsByUserId(Long userId) {
        return tableBookingRepository.findByBookedById(userId);
    }

    public TableBooking createBooking(TableBooking booking) {

        User user = userRepository.findById(booking.getBookedBy().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        Tables table = tableRepository.findById(booking.getTables().getId()).orElseThrow(() -> new RuntimeException("Table not found"));

        booking.setBookingDate(LocalDateTime.now());
        booking.setBookedBy(user);  // Set the user who is booking
        booking.setTables(table);   // Set the table being booked
        booking.setStatus("PENDING");

        return tableBookingRepository.save(booking);
    }

    public void deleteBooking(Long bookingId) {
        tableBookingRepository.deleteById(bookingId);
    }

    public List<TableBooking> getPendingBookings() {
        return tableBookingRepository.findByStatus("PENDING");
    }

    public TableBooking approveBooking(Long bookingId, Long adminId) {
        // Fetch booking and check status
        TableBooking booking = tableBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getStatus().equalsIgnoreCase("PENDING") && !booking.getStatus().equalsIgnoreCase("UPDATED")) {
            throw new RuntimeException("Booking is not in PENDING or UPDATED status.");
        }


        // Fetch admin and approve the booking
        User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
        booking.setApprovedBy(admin);
        booking.setApprovalDate(LocalDateTime.now());
        booking.setStatus("APPROVED");  // Update the status to APPROVED

        // Update the related table's status
        Tables table = booking.getTables();
        table.setStatus("BOOKED");  // Update the table status (you can set a custom status as needed)
        tableRepository.save(table);

        return tableBookingRepository.save(booking);  // Save the updated booking
    }

    public TableBooking rejectBooking(Long bookingId, Long adminId) {
        // Fetch booking and check status
        TableBooking booking = tableBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getStatus().equalsIgnoreCase("PENDING")) {
            throw new RuntimeException("Booking is not in PENDING status.");
        }

        // Fetch admin and reject the booking
        User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
        booking.setApprovedBy(admin);
        booking.setStatus("REJECTED");  // Update the status to REJECTED

        // Update the related table's status
        Tables table = booking.getTables();
        table.setStatus("AVAILABLE");  // Mark the table as AVAILABLE again
        tableRepository.save(table);  // Save the updated table

        return tableBookingRepository.save(booking);  // Save the updated booking
    }

    public TableBooking updateBooking(Long bookingId, Long userId, Long tableId) {
        // Fetch the booking, user, and table details
        TableBooking booking = tableBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Tables table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        // Update booking information
        booking.setBookedBy(user);
        booking.setTables(table);
        booking.setStatus("UPDATED");

        // Update the related table's status (if necessary)
        table.setStatus("BOOKED");  // Update the table status as required
        tableRepository.save(table);  // Save the updated table

        return tableBookingRepository.save(booking);
    }

    // Cancel a table booking
    public void cancelBooking(Long bookingId) {
        TableBooking booking = tableBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update the related table's status
        Tables table = booking.getTables();
        table.setStatus("AVAILABLE");  // Set the table status back to AVAILABLE
        tableRepository.save(table);  // Save the updated table

        // Remove the booking by deleting it
        tableBookingRepository.delete(booking);
    }

    public TableBooking freeTable(Long bookingId, Long adminId) {
        // Fetch the booking by ID
        TableBooking booking = tableBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check if the booking status is either APPROVED or BOOKED
        if (!booking.getStatus().equalsIgnoreCase("APPROVED") && !booking.getTables().getStatus().equalsIgnoreCase("BOOKED")) {
            throw new RuntimeException("Table is not in a BOOKED or APPROVED status.");
        }

        // Fetch the admin details
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Update the booking status to "FREED" and reset related table status
        booking.setStatus("FREED");
        booking.setApprovedBy(admin);  // Mark this action by the admin
        booking.setApprovalDate(LocalDateTime.now()); // Record the action time

        // Update the table status back to AVAILABLE
        Tables table = booking.getTables();
        table.setStatus("AVAILABLE");
        tableRepository.save(table); // Save the updated table status

        // Save and return the updated booking
        return tableBookingRepository.save(booking);
    }
}
