package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hub-service/tracing")
@RequiredArgsConstructor
@Tag(name = "Orders Tracing", description = "Order-tracing related endpoints")
public class OrderTracingController {

    private final IOrderHandler handler;


    @Operation(
            summary = "Get order tracing by client",
            description = "Allows a client to check the tracing of a specific order by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Tracing retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Order not found for the client")
    @ApiResponse(responseCode = "403", description = "Access denied, you must be authenticated")

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<GetTracingOrderByClientResponse> getTracingOrderByClientAndOrderId(
            @PathVariable long orderId, @AuthenticationPrincipal UserPrincipal principal) {

        long clientId = principal.id();
        GetTracingOrderByClientResponse response = handler.getTracingOrderByClient( clientId, orderId);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Get order durations by restaurant",
            description = "Allows a restaurant owner to retrieve the durations of completed orders for their restaurant"
    )
    @ApiResponse(responseCode = "200", description = "Order durations retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No completed orders found for this restaurant")
    @ApiResponse (responseCode = "403", description = "Access denied, only owners can query")

    @GetMapping("/restaurantId/{restaurantId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<GetTracingOrderDurationResponse> getTracingOrderByRestaurant(
            @PathVariable long restaurantId, @AuthenticationPrincipal UserPrincipal principal) {

        long ownerId = principal.id();
        GetTracingOrderDurationResponse response = handler.getOrderDuration(restaurantId, ownerId);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Get employee efficiency ranking by restaurant",
            description = "Allows a restaurant owner to view the average order durations per employee"
    )

    @ApiResponse(responseCode = "200", description = "Employee ranking retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No completed order records found to calculate ranking")
    @ApiResponse(responseCode = "403", description = "Access denied, only owners can query")

    @GetMapping("/restaurant/ranking/{restaurantId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<GetEmployeeRankingResponse> getRankingEmployees(
            @PathVariable long restaurantId, @AuthenticationPrincipal UserPrincipal principal) {

        long ownerId = principal.id();
        GetEmployeeRankingResponse response = handler.getEmployeeRanking(restaurantId, ownerId);
        return ResponseEntity.ok(response);
    }
}





