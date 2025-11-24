package com.foodcourt.hub.infrastructure.input.rest;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.handler.IDishHandler;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hub-service/dish/")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler handler;

    @PostMapping
    public ResponseEntity<Void> createDish(@RequestBody CreateDishCommand command){

        handler.createDish(command);

        return ResponseEntity.
                status(HttpStatus.CREATED).build();

    }

}
