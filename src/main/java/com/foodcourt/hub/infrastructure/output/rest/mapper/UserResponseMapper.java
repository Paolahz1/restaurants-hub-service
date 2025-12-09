package com.foodcourt.hub.infrastructure.output.rest.mapper;

import com.foodcourt.hub.domain.model.User;
import com.foodcourt.hub.infrastructure.output.rest.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    User toDomain(UserResponse userResponse);

}

