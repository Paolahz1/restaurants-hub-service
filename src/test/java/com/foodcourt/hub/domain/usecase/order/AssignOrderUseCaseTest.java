package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AssignOrderUseCaseTest {

    @Mock
    IOrderPersistencePort orderPersistencePort;
    @Mock
    IValidationOrdersPort validationOrdersPort;
    @Mock
    IValidationUsersPort validationUsersPort;

    @InjectMocks
    AssignOrderUseCase useCase;

    @Test
    void shouldAssignAnEmployeeToAnOrder(){

        long orderId = 1l;
        long employeeId = 2l;

        OrderItem item = OrderItem.builder()
                .dishId(1l)
                .quantity(2)
                .build();

        Order mockOrder = Order.builder()
                .restaurantId(1l)
                .items(List.of(item))
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);
        when(validationUsersPort.validateEmployeeBelongsToRestaurant(mockOrder.getRestaurantId(), employeeId)).thenReturn(true);
        when(validationOrdersPort.validateOrderStatusIsPending(any())).thenReturn(true);

        useCase.assignOrder(orderId, employeeId);

        assertEquals(OrderStatus.IN_PREPARATION, mockOrder.getStatus());
        assertEquals(2l, mockOrder.getAssignedEmployeeId());
        verify(orderPersistencePort).saveOrder(mockOrder);
    }

    @Test
    void shouldThrowForbiddenExceptionWhenOrderStatusIsNotPending() {
        long orderId = 1L;
        long employeeId = 2L;


        Order mockOrder = Order.builder()
                .status(OrderStatus.IN_PREPARATION)
                .restaurantId(1l)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);
        when(validationUsersPort.validateEmployeeBelongsToRestaurant(mockOrder.getRestaurantId(), employeeId)).thenReturn(true);
        when(validationOrdersPort.validateOrderStatusIsPending(mockOrder)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> useCase.assignOrder(orderId, employeeId));
    }


    @Test
    void shouldThrowNotFoundExceptionWhenOrderNotFound() {
        long orderId = 1L;
        long employeeId = 2L;

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> useCase.assignOrder(orderId, employeeId));
    }


}