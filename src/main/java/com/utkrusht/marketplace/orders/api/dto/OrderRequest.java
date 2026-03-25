package com.utkrusht.marketplace.orders.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Incoming payload for order creation.
 */
public class OrderRequest {

    @NotBlank(message = "customerId is required")
    private String customerId;

    @NotEmpty(message = "items must not be empty")
    @Valid
    private List<OrderItemRequest> items;

    /**
     * ISO currency code, e.g. "INR", "USD".
     */
    @NotBlank(message = "currency is required")
    private String currency;

    @NotNull(message = "totalAmount is required")
    @Positive(message = "totalAmount must be greater than 0")
    private BigDecimal totalAmount;

    public OrderRequest() {
    }

    public OrderRequest(String customerId, List<OrderItemRequest> items, String currency, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.items = items;
        this.currency = currency;
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(items, that.items) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, items, currency, totalAmount);
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "customerId='" + customerId + '\'' +
                ", items=" + items +
                ", currency='" + currency + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
