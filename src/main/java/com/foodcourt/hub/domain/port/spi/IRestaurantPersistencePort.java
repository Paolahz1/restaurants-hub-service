
package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);
    boolean existsByNit(String nit);
    Long getOwnerIdByRestaurant(Long idRestaurant);
    Restaurant findById(Long idRestaurant);
}
