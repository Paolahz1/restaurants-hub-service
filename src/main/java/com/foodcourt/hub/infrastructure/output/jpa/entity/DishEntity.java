package com.foodcourt.hub.infrastructure.output.jpa.entity;

import com.foodcourt.hub.domain.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dishes")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String urlImage;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private boolean status;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

}
