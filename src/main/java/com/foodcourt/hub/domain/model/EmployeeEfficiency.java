package com.foodcourt.hub.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeEfficiency {
    private Long employeeId;
    private long averageDurationSeconds;
}
