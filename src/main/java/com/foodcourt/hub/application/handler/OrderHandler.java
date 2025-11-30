package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.mapper.order.ICreateOrderMapper;
import com.foodcourt.hub.application.mapper.order.IPageOrdersMapper;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.api.order.*;
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
    private final IMarkOrderAsReadyServicePort markOrderAsReadyService;
    private final IMarkOrderAsDeliveredServicePort markOrderAsDeliveredService;
    private final ICancelOrderServicePort cancelOrderServicePort;

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

    @Override
    public void markOrderAsReady(long orderId, long employeeId) {
        markOrderAsReadyService.markOrderAsReady(orderId, employeeId);
    }

    @Override
    public void markOrderAsDelivered(MarkOrderAsDeliveredCommand command, long employeeId) {
        markOrderAsDeliveredService.markOrderAsDelivered(command.getOrderId(), employeeId, command.getPin());
    }

    @Override
    public void cancelOrder(long orderId, long clientId) {
        cancelOrderServicePort.cancelOrder(orderId, clientId);
    }

}
