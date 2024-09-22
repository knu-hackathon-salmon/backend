package com.knu.salmon.api.domain.member.dto.response;

import com.knu.salmon.api.domain.member.entity.Member;
import lombok.Builder;

public class MyShopProfileResponseDto {
    private String myName;
    private String myEmail;
    private String myImageUrl;

    @Builder
    public MyShopProfileResponseDto(String myName, String myEmail, String myImageUrl){
        this.myName = myName;
        this.myEmail = myEmail;
        this.myImageUrl = myImageUrl;
    }


    public static MyShopProfileResponseDto MyShopProfile(Member member){
        return MyShopProfileResponseDto.builder()
                .myName(member.getShop().getShopName())
                .myEmail(member.getEmail())
                .myImageUrl(member.getShop().getPhotoUrl())
                .build();
    }
}
