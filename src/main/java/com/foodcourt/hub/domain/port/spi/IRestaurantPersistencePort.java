
package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);
    boolean existsByNit(String nit);

}
