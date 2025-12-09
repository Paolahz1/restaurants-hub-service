package com.foodcourt.hub.infrastructure.output.rest.repository;

import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendNotificationCommand;
import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendPinCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Aquí solo estoy probando como se hacen las peticiones
 * de manera más sencilla con el uso de feign (solo con sms-service)
 */

@FeignClient(
        name = "sms-service",
        url = "http://localhost:8095/sms-service"
)
public interface ISmsFeignRepository {

    @PostMapping("/pin")
    void sendPin(@RequestBody SendPinCommand command);

    @PostMapping("/notification")
    void sendNotification(@RequestBody SendNotificationCommand command);

}
