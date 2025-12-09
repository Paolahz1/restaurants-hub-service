package com.foodcourt.hub.domain.port.api.order;

import com.foodcourt.hub.domain.model.OrderDuration;

import java.util.List;

public interface IGetOrdersDurationForRestaurantServicePort {

    List<OrderDuration> getOrdersDuration(long restaurantId, long ownerId);
}
