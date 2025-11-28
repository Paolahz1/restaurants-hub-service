package com.foodcourt.hub.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
