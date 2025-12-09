package com.foodcourt.hub.infrastructure.output.rest.adapter;

import com.foodcourt.hub.domain.port.spi.ISmsSenderPort;
import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendNotificationCommand;
import com.foodcourt.hub.infrastructure.output.rest.dto.sms.SendPinCommand;
import com.foodcourt.hub.infrastructure.output.rest.mapper.ISmsCommandMapper;
import com.foodcourt.hub.infrastructure.output.rest.repository.ISmsFeignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsSenderFeignAdapter implements ISmsSenderPort {

    private final ISmsFeignRepository feignRepository;

    private final ISmsCommandMapper smsCommandMapper;

    @Override
    public void sendTheSecurityPin(String pin, String phone) {
        SendPinCommand command = smsCommandMapper.toCommand(phone, pin);
        feignRepository.sendPin(command);
    }

    @Override
    public void sendNotification(String phone) {
        SendNotificationCommand command = smsCommandMapper.toNotificationCommand(phone);
        feignRepository.sendNotification(command);
    }
}
