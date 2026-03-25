package com.utkrusht.marketplace.orders.service;

import com.utkrusht.marketplace.orders.api.dto.OrderRequest;
import com.utkrusht.marketplace.orders.api.dto.OrderResponse;

/**
 * Orchestrates the order creation flow.
 */
public interface OrderService {

    /**
     * Creates an order after validating business rules and consulting inventory.
     *
     * @param orderRequest validated order payload
     * @return created order response
     */
    OrderResponse createOrder(OrderRequest orderRequest);
}
