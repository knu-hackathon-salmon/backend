package com.knu.salmon.api.domain.member.service;

import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.member.dto.response.MyCustomerProfileResponseDto;
import com.knu.salmon.api.domain.member.dto.response.MyShopProfileResponseDto;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    public ApiDataResponse<MyShopProfileResponseDto> getMyShopProfile(PrincipalDetails principalDetails) {
        // 현재 로그인된 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        if(member.getMemberType() == MemberType.CUSTOMER){
            throw new MemberException(MemberErrorCode.NO_MATCHING_MEMBER_EXCEPTION);
        }

        // API 응답 생성
        return ApiDataResponse.<MyShopProfileResponseDto>builder()
                .status(true)
                .code(200)
                .message("내 shop 정보 반환")
                .data(MyShopProfileResponseDto.MyShopProfile(member))
                .build();
    }

    public ApiDataResponse<MyCustomerProfileResponseDto> getMyCustomerProfile(PrincipalDetails principalDetails) {
        // 현재 로그인된 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        if(member.getMemberType() == MemberType.SHOP){
            throw new MemberException(MemberErrorCode.NO_MATCHING_MEMBER_EXCEPTION);
        }

        // API 응답 생성
        return ApiDataResponse.<MyCustomerProfileResponseDto>builder()
                .status(true)
                .code(200)
                .message("내 shop 정보 반환")
                .data(MyCustomerProfileResponseDto.MyCustomerProfile(member))
                .build();
    }




}

