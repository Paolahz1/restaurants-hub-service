package com.foodcourt.hub.infrastructure.output.sms;

import com.foodcourt.hub.domain.port.spi.ISmsSender;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsSender implements ISmsSender {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Override
    public void sendTheSecurityPin(String pin, String phone) {
        Twilio.init(accountSid, authToken);

        String formattedPhone = validateAndFormatPhone(phone);

        System.out.println("Phone: " + formattedPhone);

        Message.creator(
                        new PhoneNumber("+573168455043"),
                        new PhoneNumber("+18502900758"),
                        "¡Tu pedido está listo! Tu código de seguridad es: " + pin)
                .create();
    }

    @Override
    public void sendNotification(String phone) {
        Twilio.init(accountSid, authToken);

        String formattedPhone = validateAndFormatPhone(phone);

        Message.creator(
                        new PhoneNumber("+573168455043"),
                        new PhoneNumber("+18502900758"),
                        "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse")
                .create();
    }


    private String validateAndFormatPhone(String phone) {

        if (!phone.startsWith("+57")) {
            return "+57" + phone;
        }

        return phone;
    }
}
