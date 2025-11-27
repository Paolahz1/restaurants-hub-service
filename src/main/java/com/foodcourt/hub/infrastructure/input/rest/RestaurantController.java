package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.GetPageRestaurantsResponse;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/restaurant/")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler handler;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@RequestBody CreateRestaurantCommand command){

        CreateRestaurantResponse response = handler.createRestaurant(command);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("page/{page}/size/{size}")
    public ResponseEntity<GetPageRestaurantsResponse> getPageRestaurants(@PathVariable int page, @PathVariable int size ){
        GetPageRestaurantsResponse response = handler.getPageRestaurants(page, size);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
