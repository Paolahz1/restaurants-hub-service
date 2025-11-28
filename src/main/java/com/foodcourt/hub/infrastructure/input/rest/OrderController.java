package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/order")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order creation endpoints")
public class OrderController {

    private final IOrderHandler handler;

    @Operation(summary = "Create a new order", description = "Allows a client to create an order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only clients can create orders")
    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CreateOrderResponse> createDish(@RequestBody CreateOrderCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long clientId = userPrincipal.id();

        CreateOrderResponse response = handler.createOrder(command, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
