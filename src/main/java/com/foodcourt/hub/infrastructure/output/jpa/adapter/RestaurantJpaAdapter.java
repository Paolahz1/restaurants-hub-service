package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IRestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantJpaRepository repository;
    private final IRestaurantEntityMapper mapper;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = mapper.toEntity(restaurant);
        RestaurantEntity savedEntity = repository.save(restaurantEntity);
        return mapper.toDomain(savedEntity);
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

    @Override
    public Restaurant findById(Long idRestaurant) {
        RestaurantEntity restaurantEntity = repository.findById(idRestaurant)
                .orElse(null);

        return mapper.toDomain(restaurantEntity);
    }

    @Override
    public PageModel<Restaurant> getRestaurants(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<RestaurantEntity> entities = repository.findAll(pageable);

        return mapper.toPageModel(entities);
    }


}
