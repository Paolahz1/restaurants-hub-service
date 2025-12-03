package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSender;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkOrderAsReadyUseCaseTest {

    @Mock
    IOrderPersistencePort orderPersistencePort;

    @Mock
    ISmsSender smsSender;

    @Mock
    IOrderTracingPersistencePort orderTracingPersistencePort;

    @InjectMocks
    MarkOrderAsReadyUseCase useCase;

    @Test
    void shouldMarkOrderAsReadyAndSendPin() {
        long orderId = 1L;
        long employeeId = 2L;

        // Mocking a pending order
        Order mockOrder = Order.builder()
                .id(orderId)
                .status(OrderStatus.IN_PREPARATION)
                .restaurantId(1L)
                .assignedEmployeeId(employeeId)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        // Act
        useCase.markOrderAsReady(orderId, employeeId);

        // Assert
        assertEquals(OrderStatus.READY, mockOrder.getStatus());
        assertNotNull(mockOrder.getSecurityPin());
        assertTrue(mockOrder.getSecurityPin().matches("\\d{4}"));

        verify(orderPersistencePort).saveOrder(mockOrder);
        verify(smsSender).sendTheSecurityPin(mockOrder.getSecurityPin());
        verify(orderTracingPersistencePort).saveTracingOrder(mockOrder);

    }

    @Test
    void shouldThrowForbiddenExceptionWhenEmployeeDoesNotHaveOrderAssigned() {
        long orderId = 1L;
        long employeeId = 2L;

        Order mockOrder = Order.builder()
                .id(orderId)
                .status(OrderStatus.IN_PREPARATION)
                .restaurantId(1L)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> useCase.markOrderAsReady(orderId, employeeId));
        assertEquals(ExceptionResponse.INVALID_PERMISSION.getMessage(), exception.getError().getMessage());
    }

    @Test
    void shouldThrowForbiddenExceptionWhenOrderStatusIsNotInPreparation() {
        long orderId = 1L;
        long employeeId = 2L;

        Order mockOrder = Order.builder()
                .id(orderId)
                .status(OrderStatus.PENDING)
                .restaurantId(1L)
                .assignedEmployeeId(employeeId)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(mockOrder);

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> useCase.markOrderAsReady(orderId, employeeId));
        assertEquals(ExceptionResponse.INVALID_STATUS.getMessage(), exception.getError().getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderNotFound() {
        long orderId = 1L;
        long employeeId = 2L;

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.markOrderAsReady(orderId, employeeId));
        assertEquals(ExceptionResponse.ORDER_NOT_FOUND.getMessage(), exception.getError().getMessage());
    }
}
