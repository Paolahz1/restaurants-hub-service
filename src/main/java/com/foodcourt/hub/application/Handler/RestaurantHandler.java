package com.foodcourt.hub.application.Handler;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.mapper.IRestaurantMapper;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler{

    private final IRestaurantMapper restaurantMapper;
    private final ICreateRestaurantServicePort createRestaurantServicePort;

    @Override
    public CreateRestaurantResponse createRestaurant(CreateRestaurantCommand restaurantCommand) {

        Restaurant restaurantDomain = restaurantMapper.toDomain(restaurantCommand);
        Restaurant response = createRestaurantServicePort.create(restaurantDomain);
        return restaurantMapper.toResponse(response);



    }
}
