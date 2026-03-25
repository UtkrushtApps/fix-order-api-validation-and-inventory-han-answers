package com.utkrusht.marketplace.orders.exception;

/**
 * Thrown when the inventory service reports that requested items cannot be fulfilled.
 */
public class InventoryUnavailableException extends RuntimeException {

    public InventoryUnavailableException(String message) {
        super(message);
    }

    public InventoryUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
