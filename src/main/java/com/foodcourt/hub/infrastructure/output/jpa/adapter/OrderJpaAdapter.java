package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IOrderRepository;
import lombok.RequiredArgsConstructor;
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

        // enlazar la relación padre → hijos
        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> item.setOrder(entity));
        }

        OrderEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public List<Order> findByClientId(long clientId) {
        List<OrderEntity> entities = repository.findByClientId(clientId);
        return entities.stream().map(mapper::toDomain).toList();
    }
}
