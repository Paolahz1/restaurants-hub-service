package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.GetPageRestaurantsResponse;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler handler;

    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied — ADMIN role required")
    })
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@RequestBody CreateRestaurantCommand command){

        CreateRestaurantResponse response = handler.createRestaurant(command);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);
    }


    @Operation(summary = "Get paginated restaurants", description = "Retrieves a paginated list of restaurants.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully")
    })
    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<GetPageRestaurantsResponse> getPageRestaurants(@PathVariable int page, @PathVariable int size ){
        GetPageRestaurantsResponse response = handler.getPageRestaurants(page, size);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
