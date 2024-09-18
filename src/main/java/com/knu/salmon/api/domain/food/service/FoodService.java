package com.knu.salmon.api.domain.food.service;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.Image.repository.FoodImageRepository;
import com.knu.salmon.api.domain.Image.service.FoodImageService;
import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.global.error.custom.FoodException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;
    private final FoodImageService foodImageService;
    private final FoodImageRepository foodImageRepository;

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

    public ApiDataResponse<FoodDetailResponseDto> getFoodDetail(Long foodId){

        Food food =  foodRepository.findById(foodId).orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));;

        List<FoodImage> imageList =  foodImageService.getImageList(Optional.ofNullable(food));

        List<String> imageUrls = new ArrayList<>();

        for(FoodImage image: imageList){
            imageUrls.add(image.getImageUrl());
        }

        FoodDetailResponseDto getFoodResponseDto = FoodDetailResponseDto.builder()
                .title(food.getTitle())
                .foodId(food.getId())
                .price(food.getPrice())
                .foodCategory(food.getFoodCategory())
                .stock(food.getStock())
                .expiration(food.getExpiration())
                .name(food.getName())
                .content(food.getContent())
                .imageUrls(imageUrls)
                .build();


        return ApiDataResponse.<FoodDetailResponseDto>builder()
                .status(true)
                .code(200)
                .message("foodId : "+ foodId + " 해당하는 글 불러오기 성공!")
                .data(getFoodResponseDto)
                .build();
    }

    public ApiDataResponse<List<FoodOverviewResponseDto>> getFoodOverview(){
        List<FoodOverviewResponseDto> responseDtoList = new ArrayList<>();
        List<Food> foodList = foodRepository.findAll();

        for(Food food : foodList){
            FoodImage foodImage = foodImageRepository.findAllByFoodId(food.getId()).get(0);
            String imageUrl = foodImage.getImageUrl();

            FoodOverviewResponseDto foodOverviewResponseDto = FoodOverviewResponseDto.builder()
                    .foodId(food.getId())
                    .name(food.getName())
                    .title(food.getTitle())
                    .stock(food.getStock())
                    .price(food.getPrice())
                    .imageUrl(imageUrl)
                    .build();
            responseDtoList.add(foodOverviewResponseDto);
        }

        return ApiDataResponse.<List<FoodOverviewResponseDto>>builder()
                .status(true)
                .code(200)
                .message("음식 리스트 반환 성공!")
                .data(responseDtoList)
                .build();
    }

    public ApiBasicResponse updateFood(UpdateFoodDto updateFoodDto, PrincipalDetails principalDetails, Long foodId){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        if(!food.getMember().equals(member)){
            throw new MemberException(MemberErrorCode.NO_OWNER_EXCEPTION);
        }

        food.updateFood(updateFoodDto);

        foodRepository.save(food);

        return ApiBasicResponse.builder()
                .status(true)
                .code(200)
                .message("음식 정보업데이트 성공! " )
                .build();
    }

    public ApiBasicResponse deleteFood(PrincipalDetails principalDetails, Long foodId) {
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        if(!food.getMember().equals(member)){
            throw new MemberException(MemberErrorCode.NO_OWNER_EXCEPTION);
        }

        foodRepository.delete(food);

        return ApiBasicResponse.builder()
                .status(true)
                .code(200)
                .message("음식 삭제 성공")
                .build();
    }


}
