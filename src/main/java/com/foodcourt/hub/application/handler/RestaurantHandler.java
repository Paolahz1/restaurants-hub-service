package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.restaurant.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.restaurant.GetPageRestaurantsResponse;
import com.foodcourt.hub.application.mapper.restaurant.IPageRestaurantMapper;
import com.foodcourt.hub.application.mapper.restaurant.IRestaurantMapper;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import com.foodcourt.hub.domain.port.api.restaurant.IGetPageRestaurantsServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler{

    private final IRestaurantMapper restaurantMapper;
    private final ICreateRestaurantServicePort createRestaurantServicePort;
    private final IGetPageRestaurantsServicePort getPageRestaurantsServicePort;
    private final IPageRestaurantMapper pageRestaurantMapper;

    @Override
    public CreateRestaurantResponse createRestaurant(CreateRestaurantCommand restaurantCommand) {

        Restaurant restaurantDomain = restaurantMapper.toDomain(restaurantCommand);
        Restaurant response = createRestaurantServicePort.create(restaurantDomain);
        return restaurantMapper.toResponse(response);

    }

    @Override
    public GetPageRestaurantsResponse getPageRestaurants(int page, int size) {
        PageModel<Restaurant> pageResponse = getPageRestaurantsServicePort.getPage(page, size);
        return pageRestaurantMapper.toResponse(pageResponse);
    }
}
