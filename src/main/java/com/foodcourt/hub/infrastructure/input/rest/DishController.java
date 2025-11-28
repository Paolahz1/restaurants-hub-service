package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.*;
import com.foodcourt.hub.application.handler.IDishHandler;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
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
public class DishController {

    private final IDishHandler handler;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> createDish(@RequestBody CreateDishCommand command) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.createDish(command, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<UpdateDishResponse> updateDish(@RequestBody UpdateDishCommand command){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.updateDish(command, ownerId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("status")
    @PreAuthorize("hasRole('OWNER')")
    public  ResponseEntity<Void> updateStatusDish(@RequestBody UpdateStatusDishCommand command){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long ownerId = userPrincipal.id();
        handler.updateStatusDish(command, ownerId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("search")
    public  ResponseEntity<GetPageDishesResponse> getPageDishes( @Valid @RequestBody GetDishesCommand command
    ){
        GetPageDishesResponse response = handler.getDishes(command);
        return ResponseEntity.ok(response);
    }

}


