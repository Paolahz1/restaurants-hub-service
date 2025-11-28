package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.application.mapper.order.ICreateOrderMapper;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler{

    private final ICreateOrderServicePort servicePort;
    private final ICreateOrderMapper mapper;


    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command, long clientId) {

        Order order = servicePort.createOrder(mapper.toDomain(command), clientId);
        return  mapper.toResponse(order);
    }
}
