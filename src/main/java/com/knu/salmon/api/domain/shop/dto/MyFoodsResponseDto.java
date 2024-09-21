package com.knu.salmon.api.domain.shop.dto;

import com.knu.salmon.api.domain.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyFoodsResponseDto {
    private Long id;
    private String title;
    private String storeName;
    private int price;
    private int stock;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private String roadAddress;
    private Boolean trading;

    @Builder
    public MyFoodsResponseDto(Long id, String title, String storeName, int price, int stock, double latitude, double longitude, String imageUrl,Boolean trading, String roadAddress) {
        this.id = id;
        this.title = title;
        this.storeName = storeName;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.trading = trading;
        this.roadAddress = roadAddress;
    }


    public static MyFoodsResponseDto MyFoods(Food food){
        return MyFoodsResponseDto.builder()
                .id(food.getId())
                .title(food.getTitle())
                .storeName(food.getShop().getShopName())
                .price(food.getPrice())
                .stock(food.getStock())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .trading(food.getTrading())
                .roadAddress(food.getShop().getRoadAddress())
                .build();
    }

}
