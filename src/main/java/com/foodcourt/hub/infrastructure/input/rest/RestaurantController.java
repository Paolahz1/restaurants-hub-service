package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.restaurant.GetPageRestaurantsResponse;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/restaurant/")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Restaurant management endpoints")
public class RestaurantController {

    private final IRestaurantHandler handler;

    @Operation(summary = "Create a new restaurant", description = "Creates a restaurant. Requires ADMIN role")
    @ApiResponse(responseCode = "201", description = "Restaurant created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not authorized")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(
            @RequestBody CreateRestaurantCommand command
    ) {
        CreateRestaurantResponse response = handler.createRestaurant(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a paginated list of restaurants", description = "Returns restaurants paginated by page and size.")
    @ApiResponse(responseCode = "200", description = "Page fetched successfully")
    @GetMapping("page/{page}/size/{size}")
    public ResponseEntity<GetPageRestaurantsResponse> getPageRestaurants(
            @PathVariable int page,
            @PathVariable int size
    ) {
        GetPageRestaurantsResponse response = handler.getPageRestaurants(page, size);
        return ResponseEntity.ok(response);
    }
}
