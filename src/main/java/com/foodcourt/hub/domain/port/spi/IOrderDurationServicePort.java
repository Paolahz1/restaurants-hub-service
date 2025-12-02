package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;

import java.util.List;

public interface IOrderDurationServicePort {

    List<OrderDuration> calculateOrdersDuration(List<Order> events);


}
