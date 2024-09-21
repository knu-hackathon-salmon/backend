package com.knu.salmon.api.domain.shop.controller;

import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SwaggerShopApi {

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매중인 food 리스트 반환 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "내가 판매중인 food 리스트 반환", description = "판매중인 food 리스트 반환.")
    ResponseEntity<ApiDataResponse<List<MyFoodsResponseDto>>> getMyFoods(
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails
    );
}
