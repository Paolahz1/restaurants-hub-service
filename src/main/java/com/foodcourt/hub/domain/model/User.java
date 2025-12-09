package com.foodcourt.hub.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long id;
    private String identityDocument;
    private String phoneNumber;
    private String email;
    private String role;

}
