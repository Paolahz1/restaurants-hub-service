package com.foodcourt.hub.domain.port.api.order;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.PageModel;

public interface IGetPageOrdersServicePort {
    PageModel<Order> getPageOrder(int page, int size, String status, long employeeId);

}
