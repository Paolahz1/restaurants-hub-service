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
    public void sendTheSecurityPin(String pin) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                        new PhoneNumber("+573168455043"),
                        new PhoneNumber("+18502900758"),
                        "¡Tu pedido está listo! Tu código de seguridad es: " + pin)
                .create();

        System.out.println("Mensaje enviado: " + message.getBody());
    }

    @Override
    public void sendNotification() {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                        new PhoneNumber("+573168455043"),
                        new PhoneNumber("+18502900758"),
                        "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse")
                .create();

        System.out.println("Mensaje enviado: " + message.getBody());
    }
}
