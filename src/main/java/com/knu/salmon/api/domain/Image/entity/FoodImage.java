package com.knu.salmon.api.domain.Image.entity;


import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "food_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FoodImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Builder
    public FoodImage(String imageUrl, Food food) {
        this.imageUrl = imageUrl;
        this.food = food;
    }

}
