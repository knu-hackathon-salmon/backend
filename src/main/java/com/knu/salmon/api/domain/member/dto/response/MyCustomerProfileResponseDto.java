package com.knu.salmon.api.domain.member.dto.response;

import com.knu.salmon.api.domain.member.entity.Member;
import lombok.Builder;

public class MyCustomerProfileResponseDto {
    private String myName;
    private String myEmail;
    private String myImageUrl;

    @Builder
    public MyCustomerProfileResponseDto(String myName, String myEmail, String myImageUrl){
        this.myName = myName;
        this.myEmail = myEmail;
        this.myImageUrl = myImageUrl;
    }


    public static MyCustomerProfileResponseDto MyCustomerProfile(Member member){
        return MyCustomerProfileResponseDto.builder()
                .myName(member.getCustomer().getNickname())
                .myEmail(member.getEmail())
                .myImageUrl(member.getCustomer().getPhotoUrl())
                .build();
    }
}
