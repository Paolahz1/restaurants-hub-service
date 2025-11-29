package com.foodcourt.hub.application.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPageOrdersCommand {

    @NotBlank private int page;
    @NotBlank private int size;
    @NotBlank private String status;

}


