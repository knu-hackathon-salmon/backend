package com.knu.salmon.api.domain.food.controller;

import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.food.service.FoodService;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class ApiFoodController implements SwaggerFoodApi{

    private final FoodService foodService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> createFood(
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "foodDto") CreateFoodDto createFoodDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ApiBasicResponse apiBasicResponse = foodService.createFood(images, createFoodDto, principalDetails);
        return ResponseEntity.status(apiBasicResponse.getCode()).body(apiBasicResponse);
    }

    @GetMapping("/detail/{foodId}")
    public ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> getFoodDetail(
            @PathVariable("foodId") Long foodId) {
        ApiDataResponse<FoodDetailResponseDto> foodDetail = foodService.getFoodDetail(foodId);
        return ResponseEntity.status(foodDetail.getCode()).body(foodDetail);
    }

    @GetMapping("/overview")
    public ResponseEntity<ApiDataResponse<List<FoodOverviewResponseDto>>> getFoodOverView() {
        ApiDataResponse<List<FoodOverviewResponseDto>> foodOverview = foodService.getFoodOverview();
        return ResponseEntity.status(foodOverview.getCode()).body(foodOverview);
    }

    @PutMapping("{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> updateFood(
            @PathVariable("foodId") Long foodId,
            @RequestPart(value = "images", required = false) MultipartFile[] newImages,
            @RequestBody UpdateFoodDto updateFoodDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiBasicResponse response = foodService.updateFood(updateFoodDto, newImages, principalDetails, foodId);

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> deleteFood(
            @PathVariable("foodId") Long foodId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiBasicResponse response = foodService.deleteFood(principalDetails, foodId);

        return ResponseEntity.status(response.getCode()).body(response);
    }


}
