package com.foodcourt.hub.application.Handler;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;

public interface IRestaurantHandler {

    CreateRestaurantResponse createRestaurant(CreateRestaurantCommand restaurantCommand);
}
