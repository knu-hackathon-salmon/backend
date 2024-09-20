package com.knu.salmon.api.domain.member.controller;

import com.knu.salmon.api.domain.customer.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;


public interface SwaggerMemberApi {
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업체로 회원가입 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "업체로 회원가입", description = "업체로 회원가입")
    ResponseEntity<ApiDataResponse<ShopSignUpRequest>> shopSignUp(
            @Parameter(description = "업체 정보 dto", required = true) ShopSignUpRequest shopSignUpRequest,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails
    );

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "개인으로 회원가입 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "개인으로 회원가입", description = "개인으로 회원가입")
    ResponseEntity<ApiDataResponse<CustomerSignUpRequest>> customerSignUp(
            @Parameter(description = "개인 정보 dto", required = true) CustomerSignUpRequest customerSignUpRequest,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails
    );


}
