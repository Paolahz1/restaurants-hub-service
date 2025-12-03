package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidateDishesPort;
import com.foodcourt.hub.infrastructure.output.validation.ValidateDishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ValidationOrdersServiceConfiguration {

    private final IDishPersistencePort persistencePort;
    @Bean
    public IValidateDishesPort validationOrdersPort(){
        return new ValidateDishesService(persistencePort);
    }
}
