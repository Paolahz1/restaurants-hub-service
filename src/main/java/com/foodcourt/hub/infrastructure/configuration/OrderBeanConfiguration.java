package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.order.IAssignOrderServicePort;
import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import com.foodcourt.hub.domain.port.api.order.IGetPageOrdersServicePort;
import com.foodcourt.hub.domain.port.api.order.IMarkOrderAsReadyServicePort;
import com.foodcourt.hub.domain.port.spi.*;
import com.foodcourt.hub.domain.usecase.order.AssignOrderUseCase;
import com.foodcourt.hub.domain.usecase.order.CreateOrderUseCase;
import com.foodcourt.hub.domain.usecase.order.GetPageOrdersUseCase;
import com.foodcourt.hub.domain.usecase.order.MarkOrderAsReadyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OrderBeanConfiguration {

    private final IOrderPersistencePort persistencePort;
    private final IValidationOrdersPort validationOrdersPort;
    private final IValidationUsersPort validationUsersPort;
    private final IUserInfoPort userInfoPort;
    private final ISmsSender smsSender;

    @Bean
    public ICreateOrderServicePort createOrderServicePort (){
        return new CreateOrderUseCase(persistencePort, validationOrdersPort, validationUsersPort);
    }

    @Bean
    public IGetPageOrdersServicePort getPageOrdersServicePort(){
        return  new GetPageOrdersUseCase(persistencePort, userInfoPort);
    }

    @Bean
    public IAssignOrderServicePort assignOrderServicePort(){
        return  new AssignOrderUseCase(persistencePort,validationOrdersPort, validationUsersPort );
    }

    @Bean
    public  IMarkOrderAsReadyServicePort markOrderAsReadyServicePort(){
        return new MarkOrderAsReadyUseCase(persistencePort, validationOrdersPort, smsSender);
    }
}
