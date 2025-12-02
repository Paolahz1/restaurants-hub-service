package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.EmployeeEfficiency;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.port.api.order.IGetEmployeeAverageServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetEmployeeEfficiencyRankingUseCase implements IGetEmployeeAverageServicePort {

    private final IOrderDurationServicePort orderDurationServicePort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;

    public GetEmployeeEfficiencyRankingUseCase(IOrderDurationServicePort orderDurationServicePort, IOrderTracingPersistencePort orderTracingPersistencePort) {
        this.orderDurationServicePort = orderDurationServicePort;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
    }

    @Override
    public List<EmployeeEfficiency> getAverageOrderTracing(long restaurantId) {

        List<Order> tracingList = orderTracingPersistencePort.getTracingByRestaurant(restaurantId);
        List<OrderDuration> duration = orderDurationServicePort.calculateOrdersDuration(tracingList);

        if(duration.isEmpty()){
            throw  new NotFoundException(ExceptionResponse.TRACING_NOT_FOUND, Map.of("RsetaurantId", restaurantId));
        }

        Map<Long, Double> avgByEmployee= duration.stream().collect(Collectors.groupingBy(OrderDuration::getEmployeeId, Collectors.averagingLong(OrderDuration::getDurationInSeconds)));

        return avgByEmployee.entrySet().stream().map(
                entry -> EmployeeEfficiency.builder()
                        .employeeId(entry.getKey())
                        .averageDurationSeconds(entry.getValue().longValue())
                        .build()
        )   .sorted((a, b) -> Long.compare(a.getAverageDurationSeconds(), b.getAverageDurationSeconds()))
                .toList();
    }
}
