package com.foodcourt.hub.infrastructure.output.rest.adapter;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.infrastructure.output.rest.dto.tracing.OrderTracingCommand;
import com.foodcourt.hub.infrastructure.output.rest.dto.tracing.OrderTracingResponse;
import com.foodcourt.hub.infrastructure.output.rest.mapper.OrderTracingCommandMapper;
import com.foodcourt.hub.infrastructure.output.rest.mapper.OrderTracingResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;

@Component
@RequiredArgsConstructor
public class TracingPersistenceServiceRestAdapter implements IOrderTracingPersistencePort {

    private final WebClient tracingServiceWebClient;
    private final OrderTracingResponseMapper mapper;
    private final OrderTracingCommandMapper mapperCommand;

    @Override
    public void saveTracingOrder(Order order) {
        OrderTracingCommand command = mapperCommand.toTracingCommand(order);

        tracingServiceWebClient.post()
                .uri("/")
                .bodyValue(command)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.createException().flatMap(Mono::error)) // Manejo de errores
                .toBodilessEntity()
                .block();
    }

    @Override
    public List<Order> getTracingByClientAndOrderId(Long client, Long orderId) {
        List<OrderTracingResponse> orderTracingResponseList = tracingServiceWebClient.get()
                .uri("/client/{clientId}/order/{orderId}", client, orderId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToFlux(OrderTracingResponse.class)
                .collectList()
                .block();

        return orderTracingResponseList.isEmpty() ?  List.of() : mapper.toDomainList(orderTracingResponseList) ;
    }

    @Override
    public List<Order> getTracingByRestaurant(long restaurantId) {
        List<OrderTracingResponse> orderTracingResponseList =  tracingServiceWebClient.get()
                .uri("/restaurant/{restaurantId}", restaurantId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToFlux(OrderTracingResponse.class)
                .collectList()
                .block();

        return orderTracingResponseList.isEmpty() ? List.of() : mapper.toDomainList(orderTracingResponseList);

    }
}
