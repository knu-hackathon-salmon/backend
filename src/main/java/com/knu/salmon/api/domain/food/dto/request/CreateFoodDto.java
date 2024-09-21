package com.knu.salmon.api.domain.food.dto.request;


import com.knu.salmon.api.domain.food.entity.FoodCategory;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateFoodDto {
    private String title;

    private int stock;

    private String expiration;

    private int price;

    private String content;

    // private FoodCategory foodCategory;
}
