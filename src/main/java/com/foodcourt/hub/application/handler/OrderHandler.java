package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.mapper.order.ICreateOrderMapper;
import com.foodcourt.hub.application.mapper.order.IPageOrdersMapper;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.api.order.IAssignOrderServicePort;
import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import com.foodcourt.hub.domain.port.api.order.IGetPageOrdersServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler{

    private final ICreateOrderServicePort servicePort;
    private final IGetPageOrdersServicePort getPageOrdersServicePort;
    private final IAssignOrderServicePort assignOrderServicePort;

    private final ICreateOrderMapper mapper;
    private final IPageOrdersMapper pageOrdersMapper;



    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command, long clientId) {

        Order order = servicePort.createOrder(mapper.toDomain(command), clientId);
        return  mapper.toResponse(order);
    }

    @Override
    public GetPageOrdersResponse getPageOrders(GetPageOrdersCommand command, long employeeId) {

        PageModel<Order> pageModel = getPageOrdersServicePort.getPageOrder(
                command.getPage(), command.getSize(), command.getStatus(), employeeId);

        return  pageOrdersMapper.toResponse(pageModel);

    }

    @Override
    public void assignOrder(long orderId, long employeeId) {
        assignOrderServicePort.assignOrder(orderId, employeeId);
    }

}
