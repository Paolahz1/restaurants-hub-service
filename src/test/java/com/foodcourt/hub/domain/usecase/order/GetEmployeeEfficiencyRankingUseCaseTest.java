package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.EmployeeEfficiency;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeeEfficiencyRankingUseCaseTest {


    @Mock
    IOrderDurationServicePort durationServicePort;

    @Mock
    IOrderTracingPersistencePort tracingPersistencePort;

    @InjectMocks
    GetEmployeeEfficiencyRankingUseCase useCase;

    @Test
    void shouldCalculateAverageEfficiencyPerEmployee() {
        long restaurantId = 1L;


        List<OrderDuration> durations = List.of(
                OrderDuration.builder().orderId(1L).employeeId(101L).durationInSeconds(300L).build(),
                OrderDuration.builder().orderId(1L).employeeId(101L).durationInSeconds(500L).build(),
                OrderDuration.builder().orderId(1L).employeeId(102L).durationInSeconds(300L).build(),
                OrderDuration.builder().orderId(1L).employeeId(102L).durationInSeconds(200L).build()
        );

        when(durationServicePort.calculateOrdersDuration(anyList())).thenReturn(durations);

        List<EmployeeEfficiency> result = useCase.getAverageOrderTracing(restaurantId);

        assertEquals(2, result.size());
        assertEquals(102L, result.get(0).getEmployeeId());
        assertEquals(101L, result.get(1).getEmployeeId());
        assertEquals(250, result.get(0).getAverageDurationSeconds());
        assertEquals(400,  result.get(1).getAverageDurationSeconds());
    }

    @Test
    void shouldThrowNotFoundException() {
        long restaurantId = 1L;

        List<OrderDuration> durations = List.of();
        when(durationServicePort.calculateOrdersDuration(anyList())).thenReturn(durations);

        assertThrows(NotFoundException.class, () ->useCase.getAverageOrderTracing(restaurantId));
    }
}