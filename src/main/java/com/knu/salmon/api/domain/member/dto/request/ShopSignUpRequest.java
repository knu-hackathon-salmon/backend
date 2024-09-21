package com.knu.salmon.api.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopSignUpRequest {
    private String shopName;
    private String roadAddress;
    private String detailAddress;
    private double latitude;
    private double longitude;
    private String businessHours;
    private String phoneNumber;
    private String shopDescription;

    @Builder
    public ShopSignUpRequest(String shopName, String roadAddress, String detailAddress, double latitude, double longitude, String businessHours, String phoneNumber, String shopDescription) {
        this.shopName = shopName;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.businessHours = businessHours;
        this.phoneNumber = phoneNumber;
        this.shopDescription = shopDescription;
    }
}
