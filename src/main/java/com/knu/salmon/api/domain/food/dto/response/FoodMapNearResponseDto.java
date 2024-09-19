package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodMapNearResponseDto {
    private Long id;
    private String title;
    private String storeName;
    private String foodName;
    private Long price;
    private Long stock;
    private double latitude;
    private double longitude;
    private String imageUrl;

    @Builder
    public FoodMapNearResponseDto(Long id, String title, String storeName, String foodName, Long price, Long stock, double latitude, double longitude, String imageUrl) {
        this.id = id;
        this.title = title;
        this.storeName = storeName;
        this.foodName = foodName;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
    }

    public static FoodMapNearResponseDto fromFood(Food food){
        return FoodMapNearResponseDto.builder()
                .id(food.getId())
                .title(food.getTitle())
                .storeName(food.getShop().getShopName())
                .foodName(food.getName())
                .price(food.getPrice())
                .stock(food.getStock())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .build();
    }

}
