package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodMapNearResponseDto {
    private Long id;
    private String title;
    private String storeName;
    private int price;
    private int stock;
    private double latitude;
    private double longitude;
    private String imageUrl;

    @Builder
    public FoodMapNearResponseDto(Long id, String title, String storeName,  int price, int stock, double latitude, double longitude, String imageUrl) {
        this.id = id;
        this.title = title;
        this.storeName = storeName;
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
                .price(food.getPrice())
                .stock(food.getStock())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .build();
    }

}
