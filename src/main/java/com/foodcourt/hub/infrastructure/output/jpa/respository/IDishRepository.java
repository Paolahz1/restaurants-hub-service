package com.foodcourt.hub.infrastructure.output.jpa.respository;

import com.foodcourt.hub.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IDishRepository extends JpaRepository<DishEntity, Long> {

}

