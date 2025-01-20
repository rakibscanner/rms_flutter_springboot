package com.Project.RMSSpring.restcontroller;

import com.Project.RMSSpring.entity.Bill;
import com.Project.RMSSpring.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bills")
public class BillRestController {

    @Autowired
    private BillService billService;

    // Create a new bill for an order
    @PostMapping("/create")
    public ResponseEntity<Bill> createBill(@RequestParam Long orderId,
                                           @RequestParam Long adminId
                                           ) {
        try {
            Bill bill = billService.createBill(orderId, adminId);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // User pays the bill
    @PutMapping("/pay/{billId}")
    public ResponseEntity<Bill> payBill(@PathVariable Long billId) {
        try {
            Bill bill = billService.payBill(billId);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Admin confirms the bill
    @PutMapping("/confirm/{billId}")
    public ResponseEntity<Bill> confirmBill(@PathVariable Long billId,
                                            @RequestParam Long adminId) {
        try {
            Bill bill = billService.confirmBill(billId, adminId);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long billId) {
        Bill bill = billService.getBillById(billId);
        return ResponseEntity.ok(bill);
    }
}
