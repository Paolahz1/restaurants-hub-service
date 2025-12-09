package com.foodcourt.hub.infrastructure.output.rest.mapper;

import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendNotificationCommand;
import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendPinCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISmsCommandMapper {

    SendPinCommand toCommand(String phoneNumber, String pin);
    SendNotificationCommand toNotificationCommand(String phone);
}
