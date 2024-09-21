package com.knu.salmon.api.domain.wish.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyFoodWishResponseDto {


    private Long id;
    private String title;
    private String storeName;
    private int price;
    private int stock;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private Boolean wish;

    @Builder
    public MyFoodWishResponseDto(Long id, String title, String storeName,  int price, int stock, double latitude, double longitude, String imageUrl, Boolean wish) {
        this.id = id;
        this.title = title;
        this.storeName = storeName;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.wish = wish;
    }

    public static MyFoodWishResponseDto wishFoods(Food food){
        return MyFoodWishResponseDto.builder()
                .id(food.getId())
                .title(food.getTitle())
                .storeName(food.getShop().getShopName())
                .price(food.getPrice())
                .stock(food.getStock())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .wish(true)
                .build();
    }
}

