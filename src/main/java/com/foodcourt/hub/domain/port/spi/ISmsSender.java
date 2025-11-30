package com.foodcourt.hub.domain.port.spi;

public interface ISmsSender {

     void sendTheSecurityPin(String pin);
     void sendNotification( );
}
