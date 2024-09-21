package com.knu.salmon.api.domain.wish.controller;

import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.wish.dto.response.MyFoodWishResponseDto;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SwaggerWishApi {

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "찜 성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식잘못되었습니다"),
    })
    @Operation(summary = "음식 찜 로직", description = "음식 찜 하는 로직입니다. ")
    ResponseEntity<ApiBasicResponse> pushFoodWish(
            @Parameter(description = "음식 아이디", required = true) Long foodId,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails);




}
