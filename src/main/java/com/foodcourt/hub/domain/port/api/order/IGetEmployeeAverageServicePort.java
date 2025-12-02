package com.foodcourt.hub.domain.port.api.order;

import com.foodcourt.hub.domain.model.EmployeeEfficiency;

import java.util.List;

public interface IGetEmployeeAverageServicePort {

    List<EmployeeEfficiency> getAverageOrderTracing(long restaurantId);
}
