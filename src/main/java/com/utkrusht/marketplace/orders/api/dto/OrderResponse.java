package com.utkrusht.marketplace.orders.api.dto;

import com.utkrusht.marketplace.orders.domain.OrderStatus;

import java.time.Instant;

/**
 * Successful order creation response.
 */
public class OrderResponse {

    private String orderId;
    private OrderStatus status;
    private String message;
    private Instant createdAt;

    public OrderResponse() {
    }

    public OrderResponse(String orderId, OrderStatus status, String message, Instant createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
