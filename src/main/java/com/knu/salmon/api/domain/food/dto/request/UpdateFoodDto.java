package com.knu.salmon.api.domain.food.dto.request;

import com.knu.salmon.api.domain.food.entity.FoodCategory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateFoodDto {

    private String newTitle;

    private String newName;

    private Long newStock;

    private LocalDateTime newExpiration;

    private Long newPrice;

    private String newContent;

    private FoodCategory newFoodCategory;

    private Boolean newTrading;
}
