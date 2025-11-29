package com.foodcourt.hub.domain.port.api.restaurant;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.model.Restaurant;

public interface IGetPageRestaurantsServicePort {

    PageModel<Restaurant> getPage(int page, int size);

}
