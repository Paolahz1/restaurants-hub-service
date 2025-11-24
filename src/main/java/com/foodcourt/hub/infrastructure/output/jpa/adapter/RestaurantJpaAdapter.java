package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository repository;
    private final IRestaurantEntityMapper mapper;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = mapper.toEntity(restaurant);
        RestaurantEntity savedEntity = repository.save(restaurantEntity);
        return mapper.toDamain(savedEntity);
    }

    @Override
    public boolean existsByNit(String nit) {
        return repository.findByNit(nit).isPresent();

    }

    @Override
    public Long getOwnerIdByRestaurant(Long idRestaurant) {
        return  repository.findById(idRestaurant)
                .map(RestaurantEntity::getOwnerId)
                .orElse(null);
    }

}
