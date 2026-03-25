package com.utkrusht.marketplace.orders.api;

import com.utkrusht.marketplace.orders.api.dto.OrderRequest;
import com.utkrusht.marketplace.orders.api.dto.OrderResponse;
import com.utkrusht.marketplace.orders.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing order APIs.
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new order after validating the request and checking inventory.
     *
     * @param orderRequest the incoming order payload
     * @return 201 Created with order details when successful
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("Received create order request for customerId={} with {} items", 
                orderRequest.getCustomerId(),
                orderRequest.getItems() != null ? orderRequest.getItems().size() : 0);

        OrderResponse response = orderService.createOrder(orderRequest);

        log.info("Successfully created order with id={}", response.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
