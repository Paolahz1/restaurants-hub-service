package com.foodcourt.hub.application.dto.order;

import com.foodcourt.hub.domain.model.EmployeeEfficiency;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetEmployeeRankingResponse {
    private long restaurantId;
    private List<EmployeeEfficiency> employeeEfficiencies;
}
