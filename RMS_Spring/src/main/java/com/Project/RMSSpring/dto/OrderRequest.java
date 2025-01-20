package com.Project.RMSSpring.dto;

import com.Project.RMSSpring.entity.Order;
import com.Project.RMSSpring.entity.OrderItem;

import java.util.List;

public class OrderRequest {

    private OrderItem orderItem;
    private List<Order> orders;

    // Getters and Setters
    public OrderItem getOrderDetails() {
        return orderItem;
    }

    public void setOrderDetails(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
