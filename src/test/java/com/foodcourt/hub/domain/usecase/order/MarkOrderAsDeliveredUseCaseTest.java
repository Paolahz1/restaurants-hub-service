package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkOrderAsDeliveredUseCaseTest {

    @Mock
    IOrderPersistencePort orderPersistencePort;

    @InjectMocks
    MarkOrderAsDeliveredUseCase useCase;


    @Test
    void shouldMarkOrderAsDelivered() {
        long orderId = 1L;
        long employeeId = 2L;
        String pin = "1234";

        Order mockOrder = Order.builder()
                .id(orderId)
                .securityPin(pin)
                .status(OrderStatus.READY)
                .restaurantId(1L)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        useCase.markOrderAsDelivered(orderId, employeeId, pin);

        assertEquals(OrderStatus.DELIVERED, mockOrder.getStatus());
        verify(orderPersistencePort).saveOrder(mockOrder);

    }

    @Test
    void shouldThrowNotFoundExceptionIfOrderDoesNotExist() {
        long orderId = 1L;
        long employeeId = 2L;
        String pin = "1234";

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> useCase.markOrderAsDelivered(orderId, employeeId, pin));

        assertEquals( ExceptionResponse.ORDER_NOT_FOUND.getMessage(), exception.getError().getMessage());
    }

    @Test
    void shouldThrowForbiddenExceptionIfOrderStatusIsNotReady() {
        long orderId = 1L;
        long employeeId = 2L;
        String pin = "1234";

        Order mockOrder = Order.builder()
                .id(orderId).
                status(OrderStatus.PENDING)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> useCase.markOrderAsDelivered(orderId, employeeId, pin));

        assertEquals(ExceptionResponse.INVALID_STATUS.getMessage(), exception.getError().getMessage());
    }

    @Test
    void shouldThrowForbiddenExceptionIfPinDoesNotMatch() {
        long orderId = 1L;
        long employeeId = 2L;
        String pin = "1njnw3";

        Order mockOrder = Order.builder()
                .id(orderId)
                .status(OrderStatus.READY)
                .assignedEmployeeId(employeeId)
                .securityPin("1234")
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> useCase.markOrderAsDelivered(orderId, employeeId, pin));

        assertEquals(ExceptionResponse.INVALID_PIN.getMessage(), exception.getError().getMessage());
    }

}