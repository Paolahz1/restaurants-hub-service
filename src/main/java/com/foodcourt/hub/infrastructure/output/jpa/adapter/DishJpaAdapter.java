package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.infrastructure.output.jpa.entity.DishEntity;
import com.foodcourt.hub.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IDishRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public PageModel<Dish> getPageFromDb(int page, int size, long restaurantId, Category category) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DishEntity> entities = repository.findDishes(restaurantId, category, pageable);

        List<Dish> dishes = mapper.toDomainList(entities.getContent());

        return PageModel.<Dish>builder()
                .content(dishes)
                .page(entities.getNumber())
                .size(entities.getSize())
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .first(entities.isFirst())
                .last(entities.isLast()).
                build();
    }
}
