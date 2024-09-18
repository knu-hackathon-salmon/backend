package com.knu.salmon.api.domain.customer.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerSignUpRequest {

    private String nickname;
    private double latitude;
    private double longitude;

    @Builder
    public CustomerSignUpRequest(String nickname, double latitude, double longitude) {
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
