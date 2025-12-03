package com.foodcourt.hub.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    private long restaurantId;
    private long clientId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Instant timestamp;
    private Long assignedEmployeeId;
    private String securityPin;


    public boolean isPending() {
        return this.status == OrderStatus.PENDING;
    }

    public boolean isInPreparation() {
        return this.status == OrderStatus.IN_PREPARATION;
    }

    public boolean isReady() {
        return this.status == OrderStatus.READY;
    }

    public boolean isAssignedTo(long employeeId) {
        return this.assignedEmployeeId != null &&
                this.assignedEmployeeId.equals(employeeId);
    }

}
