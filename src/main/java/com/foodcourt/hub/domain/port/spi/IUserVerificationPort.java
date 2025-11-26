package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.infrastructure.output.rest.dto.UserResponse;

public interface IUserVerificationPort {

    String getUserRole(Long id);
    UserResponse getUser(Long id);
}
