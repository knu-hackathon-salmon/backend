package com.knu.salmon.api.domain.food.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodMyLocationRequestDto {

    private double latitude;
    private double longitude;

    @Builder
    public FoodMyLocationRequestDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
