package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.DishEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IDishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository repository;
    private final IDishEntityMapper mapper;

    @Override
    public Dish saveDish(Dish dish) {
        DishEntity entity = mapper.toEntity(dish);
        DishEntity entityResponse = repository.save(entity);
        return mapper.toDomain(entityResponse);
    }


    @Override
    public Dish findByID(Long id) {
        DishEntity dishEntity =
                repository.findById(id).
                orElse(null);
        return mapper.toDomain(dishEntity);
    }
}
