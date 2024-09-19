package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
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

    public static FoodOverviewResponseDto fromFood(Food food) {
        return FoodOverviewResponseDto.builder()
                .foodId(food.getId())
                .name(food.getName())
                .title(food.getTitle())
                .stock(food.getStock())
                .price(food.getPrice())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .build();
    }

}
