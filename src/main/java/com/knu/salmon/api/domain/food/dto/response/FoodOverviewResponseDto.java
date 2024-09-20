package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodOverviewResponseDto {

    private Long foodId;
    private String shopImageUrl;
    private String shopName;
    private String foodImageUrl;
    private String title;
    private int likesCount;
    private String roadAddress;
    private String remainingTime;
    private double distance;
    private int price;

    @Builder
    public FoodOverviewResponseDto(Long foodId, String shopImageUrl, String shopName, String foodImageUrl, String title, int likesCount, String roadAddress, String remainingTime, double distance, int price) {
        this.foodId = foodId;
        this.shopImageUrl = shopImageUrl;
        this.shopName = shopName;
        this.foodImageUrl = foodImageUrl;
        this.title = title;
        this.likesCount = likesCount;
        this.roadAddress = roadAddress;
        this.remainingTime = remainingTime;
        this.distance = distance;
        this.price = price;
    }




    public static FoodOverviewResponseDto fromFood(Food food, String remainingTime, double distance) {
        return FoodOverviewResponseDto.builder()
                .foodId(food.getId())
                .shopImageUrl(food.getShop().getPhotoUrl())
                .shopName(food.getShop().getShopName())
                .foodImageUrl(food.getImages().get(0).getImageUrl())
                .title(food.getTitle())
                .roadAddress(food.getShop().getRoadAddress())
                .remainingTime(remainingTime)
                .distance(distance)
                .likesCount(food.getLikesCount())
                .price(food.getPrice())
                .build();
    }

}
