package com.foodcourt.hub.application.dto.dish;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class GetDishesCommand {
  @NotNull
  private Long restaurantId;
  @Min(0)
  private int page;
  @Max(100)
  private int size;
  private String category;
}
