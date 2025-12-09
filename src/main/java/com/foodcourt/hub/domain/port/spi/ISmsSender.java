package com.foodcourt.hub.domain.port.spi;

public interface ISmsSender {

     void sendTheSecurityPin(String pin, String phone);
     void sendNotification( String phone);
}
