package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.order.*;
import com.foodcourt.hub.domain.port.spi.*;
import com.foodcourt.hub.domain.service.OrderDurationServicePort;
import com.foodcourt.hub.domain.usecase.order.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderBeanConfiguration {

    private final IOrderPersistencePort persistencePort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;

    private final IValidationUsersPort validationUsersPort;
    private final IValidateDishesPort validationOrdersPort;

    private final IUserInfoPort userInfoPort;
    private final ISmsSenderPort smsSender;


    @Bean
    public ICreateOrderServicePort createOrderServicePort (){
        return new CreateOrderUseCase(persistencePort, validationOrdersPort, validationUsersPort, orderTracingPersistencePort);
    }

    @Bean
    public IGetPageOrdersServicePort getPageOrdersServicePort(){
        return  new GetPageOrdersUseCase(persistencePort, userInfoPort);
    }

    @Bean
    public IAssignOrderServicePort assignOrderServicePort(){
        return  new AssignOrderUseCase(persistencePort, orderTracingPersistencePort, validationUsersPort );
    }

    @Bean
    public  IMarkOrderAsReadyServicePort markOrderAsReadyServicePort(){
        return new MarkOrderAsReadyUseCase(persistencePort, smsSender, orderTracingPersistencePort, userInfoPort);
    }

    @Bean
    public IMarkOrderAsDeliveredServicePort markOrderAsDeliveredServicePort(){
        return new MarkOrderAsDeliveredUseCase(persistencePort, orderTracingPersistencePort);
    }

    @Bean
    public  ICancelOrderServicePort cancelOrderServicePort(){
        return new CancelOrderUseCase(persistencePort, smsSender, orderTracingPersistencePort, userInfoPort);
    }

    @Bean
    public IGetTracingOrderServicePort getTracingOrderServicePort(){
        return new GetTracingOrderUseCase(orderTracingPersistencePort,persistencePort);
    }

    @Bean
    public IOrderDurationServicePort orderDurationServicePort(){
        return new OrderDurationServicePort();
    }

    @Bean
    public  IGetOrdersDurationForRestaurantServicePort getOrdersDurationForRestaurantServicePort( IOrderDurationServicePort orderDurationServicePort){
        return new GetOrdersDurationForRestaurantUseCase(orderTracingPersistencePort, orderDurationServicePort, validationUsersPort);
    }

    @Bean
    public  IGetEmployeeAverageServicePort getEmployeeAverageServicePort(IOrderDurationServicePort orderDurationServicePort){
        return new GetEmployeeEfficiencyRankingUseCase(orderDurationServicePort, orderTracingPersistencePort, validationUsersPort);
    }
}
