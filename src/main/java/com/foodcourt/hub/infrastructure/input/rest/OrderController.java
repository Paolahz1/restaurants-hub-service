package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.application.dto.order.GetPageOrdersCommand;
import com.foodcourt.hub.application.dto.order.GetPageOrdersResponse;
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
@Tag(name = "Orders", description = "Order-related endpoints")
public class OrderController {

    private final IOrderHandler handler;


    // CREATE

    @Operation(
            summary = "Create a new order",
            description = "Allows a client to create an order"
    )
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only clients can create orders")
    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long clientId = userPrincipal.id();

        CreateOrderResponse response = handler.createOrder(command, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //GET PAGE

    @Operation(
            summary = "Get paginated orders for employee",
            description = "Retrieves a page of orders from the restaurant where the employee works"
    )
    @ApiResponse(responseCode = "200", description = "Page returned correctly")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only employees can view orders")
    @PostMapping("/page")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<GetPageOrdersResponse> getPageOrders(@RequestBody GetPageOrdersCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long employeeId = userPrincipal.id();

        GetPageOrdersResponse response = handler.getPageOrders(command, employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //ASSIGN ORDER

    @Operation(
            summary = "Assign an employee to an order and change the status to 'IN_PREPARATION'",
            description = "Allows an employee to assign themselves to an order"
    )
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only employees of the restaurant can assign themselves")
    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid order status")
    @PatchMapping("/{orderId}/assign")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> assignOrder(@PathVariable Long orderId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long employeeId = userPrincipal.id();

        handler.assignOrder(orderId, employeeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}




