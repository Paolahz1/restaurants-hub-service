package com.foodcourt.hub.application.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkOrderAsDeliveredCommand {
    @NotNull  private long orderId;
    @NotBlank private String pin;
}
