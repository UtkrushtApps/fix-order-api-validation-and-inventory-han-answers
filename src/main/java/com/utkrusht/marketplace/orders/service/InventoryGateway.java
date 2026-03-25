package com.utkrusht.marketplace.orders.service;

import com.utkrusht.marketplace.orders.api.dto.OrderItemRequest;
import com.utkrusht.marketplace.orders.exception.InventoryServiceException;
import com.utkrusht.marketplace.orders.exception.InventoryUnavailableException;

import java.util.List;

/**
 * Abstraction for interacting with the external inventory system.
 */
public interface InventoryGateway {

    /**
     * Verifies that all requested items are in stock and reserves them.
     *
     * @param items order line items to check
     * @throws InventoryUnavailableException if any of the items cannot be fulfilled
     * @throws InventoryServiceException     if the inventory service itself fails or is unreachable
     */
    void checkAndReserveInventory(List<OrderItemRequest> items)
            throws InventoryUnavailableException, InventoryServiceException;
}
