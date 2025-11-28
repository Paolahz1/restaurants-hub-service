package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.restaurant.GetPageRestaurantsResponse;

public interface IRestaurantHandler {

    CreateRestaurantResponse createRestaurant(CreateRestaurantCommand restaurantCommand);
    GetPageRestaurantsResponse getPageRestaurants(int page, int size);
}
