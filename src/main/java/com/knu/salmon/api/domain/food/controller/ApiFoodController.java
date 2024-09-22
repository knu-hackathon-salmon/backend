package com.knu.salmon.api.domain.food.controller;

import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMyLocationRequestDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.food.service.FoodService;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
@Slf4j
public class ApiFoodController implements SwaggerFoodApi{

    private final FoodService foodService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> createFood(
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "createFoodDto") CreateFoodDto createFoodDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ApiDataResponse<FoodDetailResponseDto> response = foodService.createFood(images, createFoodDto, principalDetails);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/detail/{foodId}")
    public ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> getFoodDetail(
            @PathVariable("foodId") Long foodId) {
        ApiDataResponse<FoodDetailResponseDto> foodDetail = foodService.getFoodDetail(foodId);
        return ResponseEntity.status(foodDetail.getCode()).body(foodDetail);
    }

    @GetMapping("/main/overview")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<Map<String, List<FoodOverviewResponseDto>>>> getFoodOverView(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<Map<String, List<FoodOverviewResponseDto>>> response = foodService.getFoodOverview(latitude, longitude, principalDetails);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> updateFood(
            @PathVariable("foodId") Long foodId,
            @RequestPart(value = "images", required = false) MultipartFile[] newImages,
            @RequestPart(value = "updateFoodDto") UpdateFoodDto updateFoodDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<FoodDetailResponseDto> response = foodService.updateFood(updateFoodDto, newImages, principalDetails, foodId);

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

    @PostMapping("/map/near/me")
    public ResponseEntity<ApiDataResponse<List<FoodMapNearResponseDto>>> getMapNear(
            @RequestBody FoodMapNearRequestDto foodMapNearRequestDto
    ){
        ApiDataResponse<List<FoodMapNearResponseDto>> response = foodService.getMapNear(foodMapNearRequestDto);

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/map/near/box")
    public ResponseEntity<ApiDataResponse<List<FoodMapNearResponseDto>>> getFoodsInBox(
            @RequestBody FoodMapNearRequestDto foodMapNearRequestDto
    ){
        ApiDataResponse<List<FoodMapNearResponseDto>> response = foodService.getFoodsInBox(foodMapNearRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/trading/complete/{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> completeTrading (
            @PathVariable("foodId") Long foodId,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("trading/complete test 중 email은 {}", principalDetails.getEmail());
        ApiBasicResponse response = foodService.completeTrading(principalDetails, foodId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/trading/restart/{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> restartTrading (
            @PathVariable("foodId") Long foodId,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        ApiBasicResponse response = foodService.restartTrading(principalDetails, foodId);
        return ResponseEntity.status(response.getCode()).body(response);
    }


}
