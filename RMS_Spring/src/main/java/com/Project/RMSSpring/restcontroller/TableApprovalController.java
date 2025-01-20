package com.Project.RMSSpring.restcontroller;


import com.Project.RMSSpring.entity.TableBooking;
import com.Project.RMSSpring.service.TableBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/approvals")
public class TableApprovalController {

    @Autowired
    private TableBookingService tableBookingService;

    // Approve a booking
    @PutMapping("/approve/{bookingId}")
    public ResponseEntity<TableBooking> approveBooking(@PathVariable Long bookingId,
                                                       @RequestParam Long adminId) {
        TableBooking approvedBooking = tableBookingService.approveBooking(bookingId, adminId);
        return ResponseEntity.ok(approvedBooking);
    }

    // Reject a booking
    @PutMapping("/reject/{bookingId}")
    public ResponseEntity<TableBooking> rejectBooking(@PathVariable Long bookingId,
                                                      @RequestParam Long adminId) {
        TableBooking rejectedBooking = tableBookingService.rejectBooking(bookingId, adminId);
        return ResponseEntity.ok(rejectedBooking);
    }

    // Free a booked or approved table
    @PutMapping("/free/{bookingId}")
    public ResponseEntity<TableBooking> freeTable(@PathVariable Long bookingId,
                                                  @RequestParam Long adminId) {
        TableBooking freedBooking = tableBookingService.freeTable(bookingId, adminId);
        return ResponseEntity.ok(freedBooking);
    }
}
