package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.port.api.order.IGetOrdersDurationForRestaurantServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.List;
import java.util.Map;

public class GetOrdersDurationForRestaurantUseCase implements IGetOrdersDurationForRestaurantServicePort {

    private final IOrderTracingPersistencePort persistencePort;
    private final IOrderDurationServicePort servicePort;
    private final IValidationUsersPort validationUsersPort;

    public GetOrdersDurationForRestaurantUseCase(IOrderTracingPersistencePort persistencePort, IOrderDurationServicePort servicePort, IValidationUsersPort validationUsersPort) {
        this.persistencePort = persistencePort;
        this.servicePort = servicePort;
        this.validationUsersPort = validationUsersPort;
    }

    @Override
    public List<OrderDuration> getOrdersDuration(long restaurantId, long ownerId) {

        if(!validationUsersPort.validateOwnerOfRestaurant(ownerId, restaurantId)){
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("Provided ownert Id", ownerId));
        }

        List<Order> orders = persistencePort.getTracingByRestaurant(restaurantId);

        List<OrderDuration> result = servicePort.calculateOrdersDuration(orders);

        if(result.isEmpty()){
            throw  new NotFoundException(ExceptionResponse.COMPLETED_TRACING_NOT_FOUND, Map.of("RsetaurantId", restaurantId));
        }

        return result;
    }

}
