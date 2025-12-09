package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.User;

public interface IUserInfoPort {

    long getEmployeeDetails(Long id);
    User getUserById(Long id);
}
