# Solution Steps

1. Create the DTOs for the order API: an OrderItemRequest with productId and quantity, and an OrderRequest with customerId, list of items, currency, and totalAmount. Annotate all fields with Jakarta Bean Validation annotations (e.g., @NotBlank, @NotEmpty, @Positive) so Spring validates the payload automatically.

2. Create an OrderResponse DTO that represents a successful order placement, containing fields like orderId, status (enum), message, and createdAt. Add getters/setters so Jackson can serialize it.

3. Define an OrderStatus enum (e.g., PENDING, CONFIRMED, REJECTED) to represent high-level order states and use it in the OrderResponse DTO.

4. Create a reusable ErrorResponse DTO with timestamp, HTTP status, error (reason phrase), a machine-readable error code, a human-readable message, and an optional fieldErrors map. Add static factory methods to conveniently build instances for different error scenarios.

5. Define the OrderService interface with a createOrder(OrderRequest) method that returns an OrderResponse, documenting that it should orchestrate validation, inventory checks, and persistence (even if persistence is not yet implemented).

6. Define an InventoryGateway interface as an abstraction for talking to the external inventory service, with a checkAndReserveInventory(List<OrderItemRequest>) method that can throw InventoryUnavailableException for business failures and InventoryServiceException for technical failures.

7. Implement InventoryUnavailableException and InventoryServiceException as RuntimeException subclasses. Document them to clearly differentiate between "no stock" and "inventory system is down" error conditions.

8. Create a concrete InventoryGateway implementation (FakeInventoryGateway) annotated with @Component. Use an in-memory Map<String, Integer> to simulate stock levels. Implement logic to: (a) throw InventoryServiceException when a special productId (e.g., INVENTORY_DOWN) is present to simulate service outages; (b) throw InventoryUnavailableException when requested quantity exceeds available stock; and (c) reduce stock levels when reservation succeeds.

9. Implement DefaultOrderService annotated with @Service, injecting InventoryGateway. In createOrder(): log the request, call inventoryGateway.checkAndReserveInventory() with the request items, generate a new orderId (e.g., using UUID), and return an OrderResponse populated with the new id, status=CONFIRMED, a success message, and the current timestamp.

10. Create the OrderController REST controller under /api/v1/orders. Inject OrderService. Implement a POST endpoint that takes a @Valid @RequestBody OrderRequest, logs basic request details, delegates to orderService.createOrder(), and returns ResponseEntity with HTTP 201 (CREATED) and the OrderResponse body.

11. Implement a GlobalExceptionHandler using @RestControllerAdvice that extends ResponseEntityExceptionHandler. Override handleMethodArgumentNotValid to convert Bean Validation errors into a 400 response with ErrorResponse (code=VALIDATION_FAILED, fieldErrors containing field -> message). Override handleHttpMessageNotReadable to map bad JSON into a 400 MALFORMED_JSON error.

12. Add @ExceptionHandler methods in GlobalExceptionHandler for ConstraintViolationException (400 CONSTRAINT_VIOLATION), InventoryUnavailableException (409 INVENTORY_UNAVAILABLE), and InventoryServiceException (503 INVENTORY_SERVICE_FAILURE). For each, build and return an appropriate ErrorResponse and log at the right severity (debug/info/error).

13. Add a catch-all @ExceptionHandler(Exception.class) in GlobalExceptionHandler to turn any unhandled error into a 500 INTERNAL_ERROR with a generic message, and log the full stack trace for observability.

14. Wire everything together by ensuring the InventoryGateway implementation is discovered by Spring (via @Component) and the DefaultOrderService is a @Service. Confirm that the controller receives a validated OrderRequest, delegates to the service, which consults inventory and then returns an OrderResponse, while any thrown exceptions are centrally handled and mapped to the proper HTTP status codes and error payloads.

