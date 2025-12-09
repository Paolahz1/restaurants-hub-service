package com.foodcourt.hub.application.dto.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class GetPageDishesCommand {
  @NotNull
  private Long restaurantId;
  @Min(0)
  private int page;
  private int size;
  private String category;
}
