package com.knu.salmon.api.domain.food.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodOverviewResponseDto {
    private Long foodId;

    private String title;

    private String name;

    private Long stock;

    private Long price;

    private String imageUrl;


    @Builder
    public FoodOverviewResponseDto(Long foodId, String title, String name, Long stock, Long price, String imageUrl) {
        this.foodId = foodId;
        this.title = title;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
