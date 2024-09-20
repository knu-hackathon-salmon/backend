package com.knu.salmon.api.domain.message.controller;

import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
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

public interface SwaggerMessageApi {
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메세지 내역 불러오기성공!"),
            @ApiResponse(responseCode = "4XX", description = "요청 형식이 잘못되었습니다"),
    })
    @Operation(summary = "채팅룸의 메세지 불러오기 로직", description = "File은 MultiPart/form-data 형식, Dto는 application/json 형식으로 보내주셔야 해요!")
    ResponseEntity<ApiDataResponse> getMessages(
            @Parameter(description = "채팅 아이디", required = true) Long chatId,
            @Parameter(description = "사용자 정보", required = true) PrincipalDetails principalDetails);

}

