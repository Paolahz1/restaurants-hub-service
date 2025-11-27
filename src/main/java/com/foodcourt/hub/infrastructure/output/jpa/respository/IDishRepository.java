package com.foodcourt.hub.infrastructure.output.jpa.respository;

import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


    public interface IDishRepository extends JpaRepository<DishEntity, Long> {

        @Query("""
        SELECT d FROM DishEntity d
        WHERE d.restaurantId = :restaurantId
        AND d.status = true
        AND (:category IS NULL OR d.category = :category)
        """)
        Page<DishEntity> findDishes(
                @Param("restaurantId") Long restaurantId,
                @Param("category") Category category,
                Pageable pageable
        );

    }

