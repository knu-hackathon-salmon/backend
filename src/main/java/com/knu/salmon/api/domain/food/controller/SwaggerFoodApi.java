package com.knu.salmon.api.domain.food.controller;

import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMyLocationRequestDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SwaggerFoodApi {
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 생성 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "음식 생성 로직", description = "File은 MultiPart/form-data 형식, Dto는 application/json 형식으로 보내주셔야 해요!")
    ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> createFood(
            @Parameter(description = "음식 사진들", required = true) MultipartFile[] images,
            @Parameter(description = "음식 내용", required = true) CreateFoodDto createFoodDto,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails);


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 한개 조회 성공"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "음식 한 개 조회", description = "음식 한 개를 조회합니다")
    ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> getFoodDetail(
            @Parameter(description = "음식 아이디", required = true) Long foodId);


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 리스트 조회 성공"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "홈 화면에 음식 리스트 조회", description = "홈 화면에 음식 리스트를 조회합니다")
    ResponseEntity<ApiDataResponse<Map<String, List<FoodOverviewResponseDto>>>> getFoodOverView(
            @Parameter(description = "현재 내 위치 dto")FoodMyLocationRequestDto foodMyLocationRequestDto
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "음식 수정 성공!"),
                    @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
            }
    )
    @Operation(summary = "음식 수정", description = "음식을 수정합니다")
    ResponseEntity<ApiDataResponse<FoodDetailResponseDto>> updateFood(
            @Parameter(description = "음식 id") Long foodId,
            @Parameter(description = "음식 사진들", required = true) MultipartFile[] newImages,
            @Parameter(description = "음식 업데이트 dto") UpdateFoodDto updateFoodDto,
            @Parameter(description = "Bearer ey...") PrincipalDetails principalDetails);


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 삭제 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "음식 삭제 로직", description = "음식을 삭제합니다.")
    ResponseEntity<ApiBasicResponse> deleteFood(
            @Parameter(description = "음식 아이디", required = true) Long foodId,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food 리스트 반환 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "내 주위 가까운 food 리스트 반환", description = "내 주위 가까운 food 리스트 반환합니다.")
    ResponseEntity<ApiDataResponse<List<FoodMapNearResponseDto>>> getMapNear(
            @Parameter(description = "위치 정보 dto") FoodMapNearRequestDto foodMapNearRequestDto
    );

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food 리스트 반환 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "box 안에 food 리스트 반환", description = "box 안에 food 리스트 반환.")
    ResponseEntity<ApiDataResponse<List<FoodMapNearResponseDto>>> getFoodsInBox(
            @Parameter(description = "위치 정보 dto") FoodMapNearRequestDto foodMapNearRequestDto
    );

}

