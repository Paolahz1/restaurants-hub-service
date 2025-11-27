package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.*;
import com.foodcourt.hub.application.handler.IDishHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/dish")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler handler;

    @Operation(summary = "Create a new dish", description = "Creates a new dish for the authenticated OWNER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied — OWNER role required")
    })
    @PostMapping("/")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> createDish(@RequestBody CreateDishCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.createDish(command, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a dish", description = "Updates the details of a dish for the authenticated OWNER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied — OWNER role required")
    })
    @PatchMapping("/")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<UpdateDishResponse> updateDish(@RequestBody UpdateDishCommand command){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.updateDish(command, ownerId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update dish status", description = "Changes the availability status of a dish for the authenticated OWNER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish status updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied — OWNER role required")
    })
    @PatchMapping("status")
    @PreAuthorize("hasRole('OWNER')")
    public  ResponseEntity<Void> updateStatusDish(@RequestBody UpdateStatusDishCommand command){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.updateStatusDish(command, ownerId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get paginated dishes", description = "Retrieves a paginated list of dishes for a given restaurant and optional category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dishes retrieved successfully")
    })

    @GetMapping("/")
    public  ResponseEntity<GetPageDishesResponse> getPageDishes(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam long restaurantId,
            @RequestParam(required = false) String category
    ){

        GetDishesCommand command = GetDishesCommand.builder()
                .page(page)
                .size(size)
                .restaurantId(restaurantId)
                .category(category)
                .build();

        GetPageDishesResponse response = handler.getDishes(command);
        return ResponseEntity.ok(response);
    }

}


