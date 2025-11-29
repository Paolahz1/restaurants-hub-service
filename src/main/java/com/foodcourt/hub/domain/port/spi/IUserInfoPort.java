package com.foodcourt.hub.domain.port.spi;

public interface IUserInfoPort {

    String getUserRole(Long id);
    long getEmployeeDetails(Long id);
}
