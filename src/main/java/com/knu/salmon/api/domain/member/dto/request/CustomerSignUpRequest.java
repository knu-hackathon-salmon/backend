package com.knu.salmon.api.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerSignUpRequest {

    private String nickname;
    private String phoneNumber;
    private String roadAddress;
    private String detailAddress;
    private double latitude;
    private double longitude;

    @Builder
    public CustomerSignUpRequest(String nickname, String phoneNumber, String roadAddress, String detailAddress, double latitude, double longitude) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
