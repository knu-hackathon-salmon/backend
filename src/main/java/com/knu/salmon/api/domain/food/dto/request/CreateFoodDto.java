package com.knu.salmon.api.domain.food.dto.request;


import com.knu.salmon.api.domain.food.entity.FoodCategory;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateFoodDto {
    private String title;

    private String name;

    private Long stock;

    private LocalDateTime expiration;

    private Long price;

    private String content;

    private FoodCategory foodCategory;
}
