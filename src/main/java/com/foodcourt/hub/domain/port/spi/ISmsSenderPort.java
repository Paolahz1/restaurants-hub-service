package com.foodcourt.hub.domain.port.spi;

public interface ISmsSenderPort {

     void sendTheSecurityPin(String pin, String phone);
     void sendNotification( String phone);
}
