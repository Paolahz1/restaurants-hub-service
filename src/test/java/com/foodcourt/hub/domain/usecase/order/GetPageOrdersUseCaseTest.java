package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.BadRequestException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPageOrdersUseCaseTest {

    @Mock
    IOrderPersistencePort persistencePort;

    @Mock
    IUserInfoPort userInfoPort;

    @InjectMocks
    GetPageOrdersUseCase useCase;


    @Test
    void shouldReturnPageOfOrders(){
        // arange
        int page = 0;
        int size = 1;
        String status = "PENDING";
        long employeeId = 1L;
        long mockRestaurantId = 5L;

        Order mockOrder = Order.builder().build();

        PageModel<Order> mockPage = PageModel.<Order>builder()
                .content(List.of(mockOrder))
                .page(0)
                .size(1)
                .totalPages(1)
                .totalElements(1)
                .first(true)
                .last(true)
                .build();

        // mocks - arange
        when(userInfoPort.getEmployeeDetails(employeeId)).thenReturn(mockRestaurantId);
        when(persistencePort.getPageFromDb(page, size, OrderStatus.PENDING, mockRestaurantId))
                .thenReturn(mockPage);

        //act
        PageModel<Order> result = useCase.getPageOrder(page, size, status, employeeId);

        //assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(mockOrder, result.getContent().get(0));
        assertEquals(0, result.getPage());

        verify(userInfoPort).getEmployeeDetails(employeeId);
        verify(persistencePort).getPageFromDb(page, size, OrderStatus.PENDING, mockRestaurantId);
    }

    @Test
    void shouldThrowBadRequestWhenStatusIsInvalid() {

        int page = 0;
        int size = 1;
        String status = "jakjaaja";
        long employeeId = 1L;

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> useCase.getPageOrder(page, size, status, employeeId)
        );

        assertEquals("Invalid order status",exception.getError().getMessage());

        verifyNoInteractions(userInfoPort);
        verifyNoInteractions(persistencePort);
    }


}