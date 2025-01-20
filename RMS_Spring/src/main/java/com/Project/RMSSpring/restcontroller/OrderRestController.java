package com.Project.RMSSpring.restcontroller;


import com.Project.RMSSpring.entity.Order;
import com.Project.RMSSpring.entity.OrderItem;
import com.Project.RMSSpring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    // Get all orders (Admin)
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam long userId) {
        List<Order> orders = orderService.getAllOrders(userId);
        return ResponseEntity.ok(orders);
    }

    // Get orders by user ID (Customer)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Get order by ID (Admin or Customer)
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get order by Bill Id
    @GetMapping("/getOrderByBillId")
    public ResponseEntity<Order> getOrderByBillId(@RequestParam Long billId) {
        Optional<Order> order = orderService.getOrderByBillId(billId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update order status (Admin)
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveOrder(@PathVariable Long id,
                                             @RequestParam Long adminId,
                                             @RequestParam Long staffId) {
        orderService.approveOrder(id, adminId, staffId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/serve/{id}")
    public ResponseEntity<Void> serveOrder(@PathVariable Long id) {
        orderService.serveOrder(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<Void> rejectOrder(@PathVariable Long id,
                                            @RequestParam Long adminId) {
        orderService.rejectOrder(id, adminId);
        return ResponseEntity.ok().build();
    }

    // Delete an order (Admin)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
