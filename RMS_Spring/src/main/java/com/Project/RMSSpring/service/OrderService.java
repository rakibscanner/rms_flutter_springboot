package com.Project.RMSSpring.service;

import com.Project.RMSSpring.entity.Order;
import com.Project.RMSSpring.entity.OrderItem;
import com.Project.RMSSpring.entity.Role;
import com.Project.RMSSpring.entity.User;
import com.Project.RMSSpring.repository.FoodRepository;
import com.Project.RMSSpring.repository.OrderItemRepository;
import com.Project.RMSSpring.repository.OrderRepository;
import com.Project.RMSSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    public Order saveOrder(Order order) {
        try {
            if (order.getOrderItems() == null) {
                throw new IllegalArgumentException("Order Items must not be null");
            }
            double totalPrice = 0.0;
            for (OrderItem orderItem : order.getOrderItems()) {
                totalPrice += orderItem.getFood().getPrice() * orderItem.getQuantity();
                orderItemRepository.save(orderItem);
            }
            order.setTotalPrice(totalPrice);
            order.setStatus("PENDING");
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    // Get all orders
    public List<Order> getAllOrders(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Role role = user.getRole();
            if (role == Role.ADMIN || role == Role.WAITER) {
                return orderRepository.findAll();
            } else {
                return orderRepository.findAllByUser_Id(userId).orElse(new ArrayList<>());
            }
        }
        throw new IllegalArgumentException("User not found");
    }

    // Get orders by user ID (customer-specific orders)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get order by ID
    public Optional<Order> getOrderByBillId(Long billId) {
        return orderRepository.findByBill_Id(billId);
    }

    public Order approveOrder(Long orderId, Long adminId, Long staffId) {
        // Fetch the admin and order details
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order is not in pending state");
        }

        OrderItem orderItem = new OrderItem();


        order.setAdmin(admin);  // Set Admin
        order.setStaff(staff);
        order.setStatus("APPROVED");

        return orderRepository.save(order);
    }

    public Order serveOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("SERVED");
        return orderRepository.save(order);
    }

    public Order rejectOrder(Long orderId, Long adminId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        order.setStatus("REJECTED");
        order.setAdmin(admin);
        return orderRepository.save(order);
    }

    // Update order status (approve/reject by admin)
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setStatus(status);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderItem> getAllOrderDetails() {
        return orderItemRepository.findAll();
    }
}
