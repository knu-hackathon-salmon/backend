package com.knu.salmon.api.domain.wish.service;

import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.wish.dto.response.MyFoodWishResponseDto;
import com.knu.salmon.api.domain.wish.entity.Wish;
import com.knu.salmon.api.domain.wish.repository.WishRepository;
import com.knu.salmon.api.global.error.custom.FoodException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    public ApiBasicResponse createFoodWish(PrincipalDetails principalDetails, Long foodId) {
        // 현재 로그인된 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        // 음식 정보 가져오기
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        // 중복된 찜이 있는지 확인
        boolean exists = wishRepository.existsByCustomerIdAndFoodId(member.getCustomer().getId(), foodId);

        if (exists) {
            // 찜이 되어 있으면 찜을 취소
            Wish wish = wishRepository.findByCustomerIdAndFoodId(member.getCustomer().getId(), foodId);

            wishRepository.delete(wish); // 찜 삭제

            return ApiBasicResponse.builder()
                    .status(true)
                    .code(200)
                    .message("음식 찜 취소 성공")
                    .build();
        } else {
            // 찜이 되어 있지 않으면 새로운 찜 추가
            Wish wish = Wish.builder()
                    .customer(member.getCustomer())
                    .food(food)
                    .build();

            wishRepository.save(wish);

            return ApiBasicResponse.builder()
                    .status(true)
                    .code(200)
                    .message("음식 찜 성공")
                    .build();
        }
    }



    @Transactional
    public ApiDataResponse<List<MyFoodWishResponseDto>> getWishList(PrincipalDetails principalDetails) {

        // 현재 로그인된 사용자 정보 가져오기
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        List<Wish> wishes = wishRepository.findAllByCustomerId(member.getCustomer().getId());

        // 각 Wish 객체에서 Food를 추출하여 MyFoodWishResponseDto로 변환
        List<MyFoodWishResponseDto> responseDtoList = wishes.stream()
                .map(wish -> MyFoodWishResponseDto.wishFoods(wish.getFood())) // 각 Wish에서 Food 가져오기
                .toList();

        // API 응답 생성
        return ApiDataResponse.<List<MyFoodWishResponseDto>>builder()
                .status(true)
                .code(200)
                .message("내 찜 리스트 반환")
                .data(responseDtoList)
                .build();
    }


}
