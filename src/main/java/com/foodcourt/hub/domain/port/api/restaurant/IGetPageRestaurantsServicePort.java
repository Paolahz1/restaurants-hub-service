package com.foodcourt.hub.domain.port.api.restaurant;
import com.foodcourt.hub.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IGetPageRestaurantsServicePort {

    Page<Restaurant> getPage(int page, int size);

}
