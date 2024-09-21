package com.knu.salmon.api.domain.wish.dto.response;

import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyFoodWishResponseDto {


    private String myNickName;
    private String myEmail;
    private String myImageUrl;
    private Long id;
    private String title;
    private String storeName;
    private int price;
    private int stock;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private Boolean wish;
    private String roadAddress;

    @Builder
    public MyFoodWishResponseDto(String myNickName, String myEmail, String myImageUrl, Long id, String title, String storeName, int price, int stock, double latitude, double longitude, String imageUrl, Boolean wish, String roadAddress) {
        this.myNickName = myNickName;
        this.myEmail = myEmail;
        this.myImageUrl = myImageUrl;
        this.id = id;
        this.title = title;
        this.storeName = storeName;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.wish = wish;
        this.roadAddress = roadAddress;
    }



    public static MyFoodWishResponseDto wishFoods(Member member, Food food){
        return MyFoodWishResponseDto.builder()
                .myNickName(member.getCustomer().getNickname())
                .myEmail(member.getEmail())
                .myImageUrl(member.getCustomer().getPhotoUrl())
                .id(food.getId())
                .title(food.getTitle())
                .storeName(food.getShop().getShopName())
                .price(food.getPrice())
                .stock(food.getStock())
                .latitude(food.getLatitude())
                .longitude(food.getLongitude())
                .imageUrl(food.getImages().get(0).getImageUrl())
                .wish(true)
                .roadAddress(food.getShop().getRoadAddress())
                .build();
    }
}

