package com.knu.salmon.api.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShopSignUpRequest {
    private String shopName;
    private String shopDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
    private String phoneNumber;

    @Builder
    public ShopSignUpRequest(String shopName, String shopDescription, LocalDateTime startTime, LocalDateTime endTime, double latitude, double longitude, String phoneNumber) {
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
    }
}
