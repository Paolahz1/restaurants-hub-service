package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.*;
import com.foodcourt.hub.application.mapper.ICreateDishCommandMapper;
import com.foodcourt.hub.application.mapper.IPageDishesMapper;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.api.dish.IGetPageDishesServicePort;
import com.foodcourt.hub.domain.port.api.dish.IUpdateDishServicePort;
import com.foodcourt.hub.domain.port.api.dish.IUpdateStateDishServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandler implements IDishHandler{

    private final ICreateDishCommandMapper mapper;
    private final IPageDishesMapper pageDishesMapper;

    private final ICreateDishServicePort createDishServicePort;
    private final IUpdateDishServicePort updateDishServicePort;
    private final IUpdateStateDishServicePort updateStateDishServicePort;
    private final IGetPageDishesServicePort getPageDishesServicePort;

    @Override
    public void createDish(CreateDishCommand command, Long ownerId) {
        Dish dish = mapper.toDomain(command);
        createDishServicePort.create(dish, ownerId);
    }

    @Override
    public void updateDish(UpdateDishCommand command, Long ownerId) {
        updateDishServicePort.updateDish(
                command.getDishId(), command.getPrice(), command.getDescription(), ownerId
        );
    }

    @Override
    public void updateStatusDish(UpdateStatusDishCommand command, Long ownerId) {
        updateStateDishServicePort.updateStateDish(
                command.getDishId(), command.isStatus(), ownerId);
    }

    @Override
    public GetPageDishesResponse getDishes(GetDishesCommand command) {

        Category category = pageDishesMapper.map(command.getCategory());
        Page<Dish> pageDish = getPageDishesServicePort.getPage(command.getPage(), command.getSize(), command.getRestaurantId(), category);
        return pageDishesMapper.toResponse(pageDish);

    }

}
