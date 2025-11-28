package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.domain.usecase.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderBeanConfiguration {

    private final IOrderPersistencePort repository;
    private final IValidationOrdersPort validationOrdersPort;
    private final IValidationUsersPort validationUsersPort;
    @Bean
    public ICreateOrderServicePort createOrderServicePort (){
        return new CreateOrderUseCase(repository, validationOrdersPort, validationUsersPort);
    }
}
