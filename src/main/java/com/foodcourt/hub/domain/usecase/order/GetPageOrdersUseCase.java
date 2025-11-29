package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.BadRequestException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.api.order.IGetPageOrdersServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class GetPageOrdersUseCase implements IGetPageOrdersServicePort {

   private final IOrderPersistencePort orderPersistencePort;
   private final IUserInfoPort userInfoPort;

    public GetPageOrdersUseCase(IOrderPersistencePort orderPersistencePort, IUserInfoPort userInfoPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userInfoPort = userInfoPort;
    }

    @Override
    public PageModel<Order> getPageOrder(int page, int size, String status, long employeeId) {

        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BadRequestException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("Incorrect status", status)
            );
        }

        long restaurantId = userInfoPort.getEmployeeDetails(employeeId);

        return orderPersistencePort.getPageFromDb(page, size, orderStatus, restaurantId);
    }

}
