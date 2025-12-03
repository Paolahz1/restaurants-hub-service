package com.foodcourt.hub.domain.port.spi;


import com.foodcourt.hub.domain.model.OrderItem;

import java.util.List;

public interface IValidateDishesPort {

    boolean validateDishesSameRestaurant(Long restaurantId, List<OrderItem> orderItems);

}
