package com.knu.salmon.api.domain.member.controller;

import com.knu.salmon.api.domain.customer.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.service.AuthService;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class ApiMemberController implements SwaggerMemberApi{

    private final AuthService authService;

    @PostMapping("/shop/sign-up")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ShopSignUpRequest>> shopSignUp(
            @RequestBody ShopSignUpRequest shopSignUpRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<ShopSignUpRequest> response = authService.shopSignUp(shopSignUpRequest, principalDetails);

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/customer/sign-up")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<CustomerSignUpRequest>> customerSignUp(
            @RequestBody CustomerSignUpRequest customerSignUpRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApiDataResponse<CustomerSignUpRequest> response = authService.customerSignUp(customerSignUpRequest, principalDetails);

        return ResponseEntity.status(response.getCode()).body(response);
    }


}
