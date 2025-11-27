
package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);
    boolean existsByNit(String nit);
    Long getOwnerIdByRestaurant(Long idRestaurant);
    Restaurant findById(Long idRestaurant);
    Page<Restaurant> getRestaurants(int page, int size);
}
