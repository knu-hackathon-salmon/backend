package com.knu.salmon.api.domain.member.service;

import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.domain.Image.service.FoodImageService;
import com.knu.salmon.api.domain.member.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.customer.repository.CustomerRepository;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.dto.request.TempOauth2SignUpRequestDto;
import com.knu.salmon.api.domain.member.dto.response.TempTokenResponseDto;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.entity.type.Role;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.shop.entity.Shop;
import com.knu.salmon.api.domain.shop.repository.ShopRepository;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final FoodImageService foodImageService;
    private final JwtService jwtService;

    public TempTokenResponseDto tempOauth2SignUp(TempOauth2SignUpRequestDto tempOauth2SignUpRequestDto){

        String accessToken = jwtService.createAccessToken(tempOauth2SignUpRequestDto.getEmail(), Role.ROLE_NEW_USER.name());
        String refreshToken = jwtService.createRefreshToken();

        Member member = Member.builder()
                .role(Role.ROLE_NEW_USER)
                .email(tempOauth2SignUpRequestDto.getEmail())
                .refreshToken(refreshToken)
                .build();

        memberRepository.save(member);

        return TempTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ResponseEntity<ApiDataResponse<ShopSignUpRequest>> shopSignUp(ShopSignUpRequest shopSignUpRequest, MultipartFile file, PrincipalDetails principalDetails){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        if(member.getMemberType() == MemberType.SHOP || member.getMemberType() == MemberType.CUSTOMER){
            throw new MemberException(MemberErrorCode.ALREADY_JOIN_MEMBER_EXCEPTION);
        }

        member.setMemberType(MemberType.SHOP);
        memberRepository.save(member);

        String photoUrl = foodImageService.uploadImageToGcs(file);

        Shop newShop = Shop.builder()
                .shopName(shopSignUpRequest.getShopName())
                .photoUrl(photoUrl)
                .businessHours(shopSignUpRequest.getBusinessHours())
                .shopDescription(shopSignUpRequest.getShopDescription())
                .roadAddress(shopSignUpRequest.getRoadAddress())
                .detailAddress(shopSignUpRequest.getDetailAddress())
                .latitude(shopSignUpRequest.getLatitude())
                .longitude(shopSignUpRequest.getLongitude())
                .phoneNumber(shopSignUpRequest.getPhoneNumber())
                .member(member)
                .build();
        shopRepository.save(newShop);

        ApiDataResponse<ShopSignUpRequest> response = ApiDataResponse.<ShopSignUpRequest>builder()
                .status(true)
                .code(200)
                .message("상정 등록에 성공하였습니다!")
                .data(shopSignUpRequest)
                .build();

        return ResponseEntity.status(response.getCode())
                .header("type", member.getMemberType().name())
                .body(response);
    }

    public ResponseEntity<ApiDataResponse<CustomerSignUpRequest>> customerSignUp(CustomerSignUpRequest customerSignUpRequest, MultipartFile file,  PrincipalDetails principalDetails) {
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        if(member.getMemberType() == MemberType.SHOP || member.getMemberType() == MemberType.CUSTOMER){
            throw new MemberException(MemberErrorCode.ALREADY_JOIN_MEMBER_EXCEPTION);
        }

        member.setMemberType(MemberType.CUSTOMER);
        memberRepository.save(member);

        String photoUrl = foodImageService.uploadImageToGcs(file);

        Customer customer = Customer.builder()
                .nickname(customerSignUpRequest.getNickname())
                .phoneNumber(customerSignUpRequest.getPhoneNumber())
                .roadAddress(customerSignUpRequest.getRoadAddress())
                .detailAddress(customerSignUpRequest.getDetailAddress())
                .latitude(customerSignUpRequest.getLatitude())
                .longitude(customerSignUpRequest.getLongitude())
                .photoUrl(photoUrl)
                .member(member)
                .build();
        customerRepository.save(customer);

        ApiDataResponse<CustomerSignUpRequest> response = ApiDataResponse.<CustomerSignUpRequest>builder()
                .status(true)
                .code(200)
                .message("구매자 등록에 성공하였습니다!")
                .data(customerSignUpRequest)
                .build();

       return ResponseEntity.status(response.getCode())
                .header("type", member.getMemberType().name())
                .body(response);
    }

    public Member oauth2SaveOrUpdate(String email, String provider) {
        Optional<Member> optionalExistingMember = memberRepository.findByEmail(email);

        if(optionalExistingMember.isEmpty()){
            Role role = Role.ROLE_GOOGLE_USER;
            if(provider.equals("kakao")){
                role = Role.ROLE_KAKAO_USER;
            }

            Member member = Member.builder()
                    .email(email)
                    .role(role)
                    .build();

            return memberRepository.save(member);
        }

        return memberRepository.save(optionalExistingMember.get());
    }

    public String saveRefresh(String email, String refreshToken) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();

        member.setRefreshToken(refreshToken);

        memberRepository.save(member);

        return member.getRefreshToken();
    }


    public String getMyMemberType(PrincipalDetails principalDetails) {
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        return member.getMemberType().name();
    }
}
