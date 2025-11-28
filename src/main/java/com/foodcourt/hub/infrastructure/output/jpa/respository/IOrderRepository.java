package com.foodcourt.hub.infrastructure.output.jpa.respository;

import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findFirstByClientIdAndStatusIn(Long clientId, List<OrderStatus> statuses);

    Page<OrderEntity> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status, Pageable pageable);

    List<OrderEntity> findByClientId(Long clientId);

}

