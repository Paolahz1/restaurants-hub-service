package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.User;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSenderPort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseTest {

    @Mock
    IOrderPersistencePort orderPersistencePort;

    @Mock
    IOrderTracingPersistencePort orderTracingPersistencePort;

    @Mock
    ISmsSenderPort smsSender;

    @Mock
    IUserInfoPort userInfoPort;

    @InjectMocks
    CancelOrderUseCase useCase;


    @Test
    void shouldCancelOrder() {
        long orderId = 1L;
        long clientId = 10L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(clientId)
                .status(OrderStatus.PENDING)
                .build();

        User mockUser= User.builder()
                .phoneNumber("3168455043")
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(order);
        when(userInfoPort.getUserById(clientId)).thenReturn(mockUser);

        useCase.cancelOrder(orderId, clientId);

        assertEquals(OrderStatus.CANCELED, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
        verify(smsSender, never()).sendNotification("3168455043");
        verify(orderTracingPersistencePort).saveTracingOrder(order);
    }



    @Test
    void shouldThrowNotFoundIfOrderDoesNotExist() {
        long orderId = 1L;

        when(orderPersistencePort.findByOrderId(orderId))
                .thenReturn(null);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> useCase.cancelOrder(orderId, 20L)
        );

        assertEquals(ExceptionResponse.ORDER_NOT_FOUND.getMessage(), exception.getError().getMessage());
    }



    @Test
    void shouldThrowForbiddenIfClientIsNotOwner() {
        long orderId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(10L)
                .status(OrderStatus.PENDING)
                .build();

        when(orderPersistencePort.findByOrderId(orderId))
                .thenReturn(order);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> useCase.cancelOrder(orderId, 99L)
        );

        assertEquals(ExceptionResponse.INVALID_PERMISSION.getMessage(), exception.getError().getMessage());
    }


    @Test
    void shouldThrowForbiddenAndSendSmsIfOrderIsNotPending() {
        long orderId = 1L;
        long clientId = 10L;


        User mockUser= User.builder()
                .phoneNumber("3168455043")
                .build();

        Order order = Order.builder()
                .id(orderId)
                .clientId(clientId)
                .status(OrderStatus.READY)
                .build();

        when(orderPersistencePort.findByOrderId(orderId)).thenReturn(order);
        when(userInfoPort.getUserById(clientId)).thenReturn(mockUser);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> useCase.cancelOrder(orderId, clientId)
        );

        assertEquals(ExceptionResponse.INVALID_STATUS.getMessage(), exception.getError().getMessage());

        verify(smsSender).sendNotification("3168455043");
        verify(orderPersistencePort, never()).saveOrder(any());
    }
}
