package com.knu.salmon.api.domain.food.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodMapNearRequestDto {

    private double neLat;
    private double neLng;
    private double swLat;
    private double swLng;
    private double userLat;
    private double userLng;

    @Builder
    public FoodMapNearRequestDto(double neLat, double neLng, double swLat, double swLng, double userLat, double userLng) {
        this.neLat = neLat;
        this.neLng = neLng;
        this.swLat = swLat;
        this.swLng = swLng;
        this.userLat = userLat;
        this.userLng = userLng;
    }
}
