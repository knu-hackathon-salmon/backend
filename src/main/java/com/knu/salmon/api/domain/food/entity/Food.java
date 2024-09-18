package com.knu.salmon.api.domain.food.entity;

import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "foods")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "food_name")
    private String name;

    @Column(name = "food_stock")
    private Long stock;

    @Column(name = "food_expiration")
    private LocalDateTime expiration;

    @Column(name = "food_price")
    private Long price;

    @Column(name = "food_content")
    private String content;

    @Column(name = "food_category")
    private FoodCategory foodCategory;

    @Builder
    public Food(String name, Long stock, LocalDateTime expiration, Long price, String content, FoodCategory foodCategory){
        this.name = name;
        this.stock = stock;
        this.expiration = expiration;
        this.price = price;
        this.content = content;
        this.foodCategory = foodCategory;
    }



}
