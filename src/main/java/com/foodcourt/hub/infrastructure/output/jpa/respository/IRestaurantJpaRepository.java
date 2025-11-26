package com.foodcourt.hub.infrastructure.output.jpa.respository;

import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity>  findByNit(String nit);
}

