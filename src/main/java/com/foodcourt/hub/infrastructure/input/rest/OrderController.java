package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.*;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
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
public class OrderController {

    private final IOrderHandler handler;

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


