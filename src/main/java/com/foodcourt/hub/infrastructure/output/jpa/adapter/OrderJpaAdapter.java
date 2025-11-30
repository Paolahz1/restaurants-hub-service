package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IOrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository repository;
    private final IOrderEntityMapper mapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Order> findByClientId(long clientId) {
        List<OrderEntity> entities = repository.findByClientId(clientId);
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public PageModel<Order> getPageFromDb(int page, int size, OrderStatus status, long restaurantId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderEntity> pageEntity =
                repository.findByRestaurantIdAndStatus(restaurantId, status, pageable);
        return  mapper.toPageModel(pageEntity);
    }

    @Override
    public Order findByOrderId(long orderId) {
        OrderEntity entity = repository.findById(orderId)
                .orElse(null);
        return mapper.toDomain(entity);
    }

    @Override
    public void deleteOrder(long orderId) {

        OrderEntity entity = repository.findById(orderId)
                .orElse(null);

        if (entity != null)  repository.delete(entity);
    }


}


