package com.utkrusht.marketplace.orders.service.impl;

import com.utkrusht.marketplace.orders.api.dto.OrderItemRequest;
import com.utkrusht.marketplace.orders.exception.InventoryServiceException;
import com.utkrusht.marketplace.orders.exception.InventoryUnavailableException;
import com.utkrusht.marketplace.orders.service.InventoryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple in-memory implementation of {@link InventoryGateway} used for demonstration.
 *
 * <p>In a production system this would perform an HTTP call to an external
 * inventory service and translate errors into domain-specific exceptions.</p>
 */
@Component
public class FakeInventoryGateway implements InventoryGateway {

    private static final Logger log = LoggerFactory.getLogger(FakeInventoryGateway.class);

    private final Map<String, Integer> stock = new HashMap<>();

    public FakeInventoryGateway() {
        // Seed with some demo stock levels.
        stock.put("P1", 10);
        stock.put("P2", 0); // out of stock to demonstrate unavailable error
        stock.put("P3", 5);
    }

    @Override
    public void checkAndReserveInventory(List<OrderItemRequest> items)
            throws InventoryUnavailableException, InventoryServiceException {

        if (items == null || items.isEmpty()) {
            return; // Nothing to do, validation already prevents this
        }

        // Special hook to simulate a technical failure of inventory system.
        boolean simulateServiceFailure = items.stream()
                .anyMatch(i -> "INVENTORY_DOWN".equalsIgnoreCase(i.getProductId()));
        if (simulateServiceFailure) {
            log.error("Simulating inventory service failure for test scenario");
            throw new InventoryServiceException("Inventory service is currently unavailable");
        }

        // Check availability for each item.
        StringBuilder unavailableSummary = new StringBuilder();

        for (OrderItemRequest item : items) {
            String productId = item.getProductId();
            int requestedQty = item.getQuantity();

            Integer available = stock.get(productId);
            if (available == null || available < requestedQty) {
                if (unavailableSummary.length() > 0) {
                    unavailableSummary.append(", ");
                }
                unavailableSummary
                        .append(productId)
                        .append(" (requested=")
                        .append(requestedQty)
                        .append(", available=")
                        .append(available == null ? 0 : available)
                        .append(')');
            }
        }

        if (unavailableSummary.length() > 0) {
            String message = "Insufficient inventory for: " + unavailableSummary;
            log.warn(message);
            throw new InventoryUnavailableException(message);
        }

        // If we reach here, all items are available; reserve them.
        for (OrderItemRequest item : items) {
            String productId = item.getProductId();
            int requestedQty = item.getQuantity();
            stock.computeIfPresent(productId, (k, v) -> v - requestedQty);
        }

        log.debug("Reserved inventory for {} items", items.size());
    }
}
