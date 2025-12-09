package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.EmployeeEfficiency;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.port.api.order.IGetEmployeeAverageServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetEmployeeEfficiencyRankingUseCase implements IGetEmployeeAverageServicePort {

    private final IOrderDurationServicePort orderDurationServicePort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;
    private final IValidationUsersPort validationUsersPort;

    public GetEmployeeEfficiencyRankingUseCase(IOrderDurationServicePort orderDurationServicePort, IOrderTracingPersistencePort orderTracingPersistencePort, IValidationUsersPort validationUsersPort) {
        this.orderDurationServicePort = orderDurationServicePort;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
        this.validationUsersPort = validationUsersPort;
    }

    @Override
    public List<EmployeeEfficiency> getAverageOrderTracing(long restaurantId, long ownerId) {

        if(!validationUsersPort.validateOwnerOfRestaurant(ownerId, restaurantId)){
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("Provided owner Id", ownerId));
        }

        List<Order> tracingList = orderTracingPersistencePort.getTracingByRestaurant(restaurantId);
        List<OrderDuration> duration = orderDurationServicePort.calculateOrdersDuration(tracingList);

        if(duration.isEmpty()){
            throw  new NotFoundException(ExceptionResponse.COMPLETED_TRACING_NOT_FOUND, Map.of("RsetaurantId", restaurantId));
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
