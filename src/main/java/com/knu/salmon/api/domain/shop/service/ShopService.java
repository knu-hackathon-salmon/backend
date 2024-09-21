package com.knu.salmon.api.domain.shop.service;

import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import com.knu.salmon.api.domain.shop.repository.ShopRepository;
import com.knu.salmon.api.domain.wish.dto.response.MyFoodWishResponseDto;
import com.knu.salmon.api.domain.wish.entity.Wish;
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
public class ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    public ApiDataResponse<List<MyFoodsResponseDto>> getMyFoods(PrincipalDetails principalDetails) {
        // 현재 로그인된 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        if(member.getMemberType() == MemberType.CUSTOMER){
            throw new MemberException(MemberErrorCode.CAN_NOT_ROAD_ALL_FOODS_EXCEPTION);
        }

        List<Food> foods = foodRepository.findAllByShop(member.getShop());

        // 각 Wish 객체에서 Food를 추출하여 MyFoodWishResponseDto로 변환
        List<MyFoodsResponseDto> responseDtoList = foods.stream()
                .map(MyFoodsResponseDto::MyFoods) // 각 Wish에서 Food 가져오기
                .toList();

        // API 응답 생성
        return ApiDataResponse.<List<MyFoodsResponseDto>>builder()
                .status(true)
                .code(200)
                .message("내 음식 판매리스트 반환")
                .data(responseDtoList)
                .build();
    }

}
