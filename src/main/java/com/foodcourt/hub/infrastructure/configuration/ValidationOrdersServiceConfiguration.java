package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.domain.service.ValidationOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ValidationOrdersServiceConfiguration {

    private final IDishPersistencePort persistencePort;
    @Bean
    public IValidationOrdersPort validationOrdersPort(){
        return new ValidationOrdersService(persistencePort);
    }
}
