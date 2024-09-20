package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.entity.FoodCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FoodDetailResponseDto {
    private Long foodId;
    private String title;
    private String name;
    private Long stock;
    private LocalDateTime expiration;
    private Long price;
    private String content;
    private FoodCategory foodCategory;
    private List<String> imageUrls;
    private LocalDateTime createdDate;
    private double latitude;
    private double longitude;
    private Boolean trading;
    private String shopName;

    @Builder
    public FoodDetailResponseDto(Long foodId, String title, String name, Long stock, LocalDateTime expiration, Long price, String content, FoodCategory foodCategory, List<String> imageUrls, LocalDateTime createdDate, double latitude, double longitude, Boolean trading, String shopName) {
        this.foodId = foodId;
        this.title = title;
        this.name = name;
        this.stock = stock;
        this.expiration = expiration;
        this.price = price;
        this.content = content;
        this.foodCategory = foodCategory;
        this.imageUrls = imageUrls;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trading = trading;
        this.shopName = shopName;
    }



    public static FoodDetailResponseDto fromFood(Food food){
        return FoodDetailResponseDto.builder()
                .foodId(food.getId())
                .title(food.getTitle())
                .name(food.getName())
                .stock(food.getStock())
                .expiration(food.getExpiration())
                .price(food.getPrice())
                .content(food.getContent())
                .imageUrls(food.getImages().stream().map(FoodImage::getImageUrl).toList())
                .createdDate(food.getCreatedAt())
                .trading(food.getTrading())
                .shopName(food.getShop().getShopName())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .build();

    }
}

