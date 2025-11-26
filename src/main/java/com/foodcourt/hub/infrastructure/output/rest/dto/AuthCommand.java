package com.foodcourt.hub.infrastructure.output.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCommand {
    private String email;
    private String password;
}

