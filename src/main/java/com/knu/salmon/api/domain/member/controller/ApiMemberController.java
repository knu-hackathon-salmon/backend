package com.knu.salmon.api.domain.member.controller;

import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.domain.member.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.dto.request.TempOauth2SignUpRequestDto;
import com.knu.salmon.api.domain.member.dto.response.TempTokenResponseDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.service.AuthService;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class ApiMemberController implements SwaggerMemberApi {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/oauth2/temp")
    public ResponseEntity<ApiBasicResponse> tempOauth2SignUp(
            @RequestBody TempOauth2SignUpRequestDto tempOauth2SignUpRequestDto,
            HttpServletResponse httpServletResponse
    ) {

        TempTokenResponseDto tempTokenResponseDto = authService.tempOauth2SignUp(tempOauth2SignUpRequestDto);

        String accessToken = tempTokenResponseDto.getAccessToken(); // 예시로, 실제 접근 방식에 따라 변경
        String refreshToken = tempTokenResponseDto.getRefreshToken(); // 예시로, 실제 접근 방식에 따라 변경

        // 응답 헤더에 Access Token 추가
        httpServletResponse.setHeader("Authorization", accessToken);

        // Refresh Token을 쿠키에 추가
        httpServletResponse.addCookie(jwtService.createCookie("Authorization-refresh", refreshToken));

        // 응답 본체를 설정하고 반환
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(ApiBasicResponse.builder()
                .status(true)
                .code(200)
                .message("효은님 헤더에 액세스 토큰, 쿠키에 리프레시 있어요")
                .build());
    }

    @PostMapping("/shop/sign-up")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ShopSignUpRequest>> shopSignUp(
            @RequestPart("shopSignUpRequest") ShopSignUpRequest shopSignUpRequest,
            @RequestPart("uploadPhoto") MultipartFile file,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<ShopSignUpRequest> response = authService.shopSignUp(shopSignUpRequest, file, principalDetails);

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/customer/sign-up")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<CustomerSignUpRequest>> customerSignUp(
            @RequestPart("customerSignUpRequest") CustomerSignUpRequest customerSignUpRequest,
            @RequestPart("uploadPhoto") MultipartFile file,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<CustomerSignUpRequest> response = authService.customerSignUp(customerSignUpRequest, file, principalDetails);

        return ResponseEntity.status(response.getCode()).body(response);
    }


}
