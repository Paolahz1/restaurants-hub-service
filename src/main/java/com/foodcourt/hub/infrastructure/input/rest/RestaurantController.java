package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hub-service/restaurant/")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler handler;

    @PostMapping
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@RequestBody CreateRestaurantCommand command){

        CreateRestaurantResponse response = handler.createRestaurant(command);

        if(!response.isSuccess()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);

    }

}
