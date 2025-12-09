package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class GetOrdersDurationForRestaurantUseCaseTest {

    @Mock
    IOrderTracingPersistencePort orderTracingPersistencePort;

    @Mock
    IOrderDurationServicePort servicePort;

    @Mock
    IValidationUsersPort validationUsersPort;

    @InjectMocks
    GetOrdersDurationForRestaurantUseCase useCase;

    @Test
    void shouldGetOrdersDuration() {
        long restaurantId = 1l;
        long ownerId = 1l;

        List<Order> mockTracingOrders = List.of(Order.builder().build());

        OrderDuration duration = OrderDuration.builder()
                .orderId(1L)
                .employeeId(2L)
                .durationInSeconds(300L)
                .build();

        when(validationUsersPort.validateOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(true);
        when(orderTracingPersistencePort.getTracingByRestaurant(restaurantId)).thenReturn(mockTracingOrders);
        when(servicePort.calculateOrdersDuration(mockTracingOrders)).thenReturn(List.of(duration));
        List<OrderDuration> result = useCase.getOrdersDuration(restaurantId,ownerId );

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(2L, result.get(0).getEmployeeId());
        assertEquals(300L, result.get(0).getDurationInSeconds());

    }

    @Test
    void shouldThrowNotFound() {
        long restaurantId = 1l;
        long ownerId = 1l;

        List<Order> mockTracingOrders = List.of(Order.builder().build());
        when(validationUsersPort.validateOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(true);
        when(orderTracingPersistencePort.getTracingByRestaurant(restaurantId)).thenReturn(mockTracingOrders);
        when(servicePort.calculateOrdersDuration(mockTracingOrders)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> useCase.getOrdersDuration(restaurantId, ownerId));


    }
}
