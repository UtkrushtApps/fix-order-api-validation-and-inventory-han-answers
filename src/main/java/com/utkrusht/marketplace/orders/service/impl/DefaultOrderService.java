package com.utkrusht.marketplace.orders.service.impl;

import com.utkrusht.marketplace.orders.api.dto.OrderRequest;
import com.utkrusht.marketplace.orders.api.dto.OrderResponse;
import com.utkrusht.marketplace.orders.domain.OrderStatus;
import com.utkrusht.marketplace.orders.service.InventoryGateway;
import com.utkrusht.marketplace.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Default implementation of {@link OrderService}.
 */
@Service
public class DefaultOrderService implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);

    private final InventoryGateway inventoryGateway;

    public DefaultOrderService(InventoryGateway inventoryGateway) {
        this.inventoryGateway = inventoryGateway;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // At this point, basic Bean Validation has already run on the DTO.
        log.debug("Starting order creation for customerId={}", orderRequest.getCustomerId());

        // 1. Consult inventory service and reserve stock.
        inventoryGateway.checkAndReserveInventory(orderRequest.getItems());

        // 2. In a real system, persist the order in the database here.
        String orderId = UUID.randomUUID().toString();

        log.info("Order {} confirmed for customerId={} with {} items", orderId,
                orderRequest.getCustomerId(), orderRequest.getItems().size());

        // 3. Build response DTO.
        OrderResponse response = new OrderResponse();
        response.setOrderId(orderId);
        response.setStatus(OrderStatus.CONFIRMED);
        response.setMessage("Order created successfully");
        response.setCreatedAt(Instant.now());
        return response;
    }
}
