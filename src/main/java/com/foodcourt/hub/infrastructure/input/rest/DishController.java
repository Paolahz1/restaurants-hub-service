package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.*;
import com.foodcourt.hub.application.handler.IDishHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/dish/")
@RequiredArgsConstructor
@Tag(name = "Dishes", description = "Dish management endpoints")
public class DishController {

    private final IDishHandler handler;

    @Operation(summary = "Create a new dish", description = "Allows a restaurant owner to create a dish")
    @ApiResponse(responseCode = "201", description = "Dish created")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only owners can create dishes")
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> createDish(@RequestBody CreateDishCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();

        handler.createDish(command, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Update dish information", description = "Allows a restaurant owner to update description or price of a dish.")
    @ApiResponse(responseCode = "200", description = "Dish updated")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only owners of the given restaurant can update dishes")
    @PatchMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> updateDish(@RequestBody UpdateDishCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();

        handler.updateDish(command, ownerId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Update dish status", description = "Enable or disable a dish")
    @ApiResponse(responseCode = "200", description = "Status updated")
    @ApiResponse(responseCode = "403", description = "Forbidden - Only owners can update status")
    @PatchMapping("status")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> updateStatusDish(@RequestBody UpdateStatusDishCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();

        handler.updateStatusDish(command, ownerId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Search dishes with filters", description = "Returns paginated dishes filtered by restaurant or category")
    @ApiResponse(responseCode = "200", description = "Dishes retrieved successfully")
    @PostMapping("search")
    public ResponseEntity<GetPageDishesResponse> getPageDishes(@Valid @RequestBody GetDishesCommand command) {
        GetPageDishesResponse response = handler.getDishes(command);
        return ResponseEntity.ok(response);
    }
}
