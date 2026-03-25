package com.utkrusht.marketplace.orders.exception;

/**
 * Thrown when there is a technical failure talking to the inventory service
 * (e.g. timeouts, 5xx responses, network issues).
 */
public class InventoryServiceException extends RuntimeException {

    public InventoryServiceException(String message) {
        super(message);
    }

    public InventoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
