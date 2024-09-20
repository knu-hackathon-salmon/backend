package com.knu.salmon.api.domain.member.controller;

import com.knu.salmon.api.domain.member.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.service.AuthService;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class ApiMemberController implements SwaggerMemberApi{

    private final AuthService authService;

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
