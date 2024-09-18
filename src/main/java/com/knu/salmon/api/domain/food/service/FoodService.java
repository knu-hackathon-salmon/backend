package com.knu.salmon.api.domain.food.service;

import com.knu.salmon.api.domain.Image.service.FoodImageService;
import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;
    private final FoodImageService foodImageService;

    public ApiBasicResponse createFood(MultipartFile[] files, CreateFoodDto createFoodDto, PrincipalDetails principalDetails){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Food food = Food.builder()
                .name(createFoodDto.getName())
                .foodCategory(createFoodDto.getFoodCategory())
                .stock(createFoodDto.getStock())
                .price(createFoodDto.getPrice())
                .content(createFoodDto.getContent())
                .expiration(createFoodDto.getExpiration())
                .build();

        foodRepository.save(food);

        foodImageService.uploadToBoardImages(files, food);

        //멤버 food 추가 시켜주기

        foodRepository.save(food);

        ApiBasicResponse apiBasicResponse = ApiBasicResponse.builder()
                .status(true)
                .code(200)
                .message("음식 생성 성공!")
                .build();

        return apiBasicResponse;
    }





}
