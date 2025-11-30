package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.DishesNotFromSameRestaurant;
import com.foodcourt.hub.domain.exception.HasPendingOrdersException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    IOrderPersistencePort persistencePort;

    @Mock
    IValidationOrdersPort validationOrdersPort;

    @Mock
    IValidationUsersPort validationUsersPort;

    @InjectMocks
    CreateOrderUseCase useCase;


    @Test
    void shouldCreateOrder(){

        long clientId = 1l;
        OrderItem item = OrderItem.builder()
                .dishId(1l)
                .quantity(2)
                .build();

        Order mockOrder = Order.builder()
                .restaurantId(1l)
                .items(List.of(item))
                .build();

        when(validationUsersPort.clientHasPendingOrders(clientId)).thenReturn(false);
        when(validationOrdersPort.validateDishesSameRestaurant(
                mockOrder.getRestaurantId(), mockOrder.getItems())).
                thenReturn(true);
        when(persistencePort.saveOrder(mockOrder)).thenReturn(mockOrder);

        useCase.createOrder(mockOrder,clientId);

        assertEquals(OrderStatus.PENDING, mockOrder.getStatus());
        assertEquals(clientId, mockOrder.getClientId());
        verify(persistencePort).saveOrder(mockOrder);

    }

    @Test
    void shouldThrowIllegalArgumentException(){

        long clientId = 1l;

        Order mockOrder = Order.builder().build();

        assertThrows(IllegalArgumentException.class, () -> useCase.createOrder(mockOrder, clientId));

    }

    @Test
    void shouldThrowHasPendingOrdersException(){

        long clientId = 1l;
        OrderItem item = OrderItem.builder()
                .dishId(1l)
                .quantity(2)
                .build();

        Order mockOrder = Order.builder()
                .restaurantId(1l)
                .items(List.of(item))
                .build();


        when(validationUsersPort.clientHasPendingOrders(clientId)).thenReturn(true);
        assertThrows(HasPendingOrdersException.class, () -> useCase.createOrder(mockOrder, clientId));

    }

    @Test
    void shouldThrowDishesNotFromSameRestaurantException(){

        long clientId = 1l;
        OrderItem item = OrderItem.builder()
                .dishId(1l)
                .quantity(2)
                .build();

        OrderItem item2 = OrderItem.builder()
                .dishId(5l)
                .quantity(2)
                .build();

        Order mockOrder = Order.builder()
                .restaurantId(1l)
                .items(List.of(item, item2))
                .build();


        when(validationUsersPort.clientHasPendingOrders(clientId)).thenReturn(false);
        when(validationOrdersPort.validateDishesSameRestaurant(
                mockOrder.getRestaurantId(), mockOrder.getItems())).thenReturn(false);
        assertThrows(DishesNotFromSameRestaurant.class, () -> useCase.createOrder(mockOrder, clientId));

    }
}