package com.knu.salmon.api.domain.member.service;

import com.knu.salmon.api.domain.customer.dto.request.CustomerSignUpRequest;
import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.customer.repository.CustomerRepository;
import com.knu.salmon.api.domain.member.dto.request.ShopSignUpRequest;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.entity.type.Role;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.seller.entity.Shop;
import com.knu.salmon.api.domain.seller.repository.ShopRepository;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    public ApiDataResponse<ShopSignUpRequest> shopSignUp(ShopSignUpRequest shopSignUpRequest, PrincipalDetails principalDetails){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));
        member.setMemberType(MemberType.SHOP);
        memberRepository.save(member);

        log.info("member email : {}", member.getEmail());

        Shop newShop = Shop.builder()
                .shopName(shopSignUpRequest.getShopName())
                .startTime(shopSignUpRequest.getStartTime())
                .endTime(shopSignUpRequest.getEndTime())
                .shopDescription(shopSignUpRequest.getShopDescription())
                .latitude(shopSignUpRequest.getLatitude())
                .longitude(shopSignUpRequest.getLongitude())
                .phoneNumber(shopSignUpRequest.getPhoneNumber())
                .member(member)
                .build();
        shopRepository.save(newShop);


        return ApiDataResponse.<ShopSignUpRequest>builder()
                .status(true)
                .code(200)
                .message("상정 등록에 성공하였습니다!")
                .data(shopSignUpRequest)
                .build();
    }

    public ApiDataResponse<CustomerSignUpRequest> customerSignUp(CustomerSignUpRequest customerSignUpRequest, PrincipalDetails principalDetails) {
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Customer customer = Customer.builder()
                .nickname(customerSignUpRequest.getNickname())
                .latitude(customerSignUpRequest.getLatitude())
                .longitude(customerSignUpRequest.getLongitude())
                .member(member)
                .build();

        member.setMemberType(MemberType.CUSTOMER);
        memberRepository.save(member);

        customerRepository.save(customer);

        return ApiDataResponse.<CustomerSignUpRequest>builder()
                .status(true)
                .code(200)
                .message("구매자 등록에 성공하였습니다!")
                .data(customerSignUpRequest)
                .build();
    }

    public Member oauth2SaveOrUpdate(String email, String provider) {
        Optional<Member> optionalExistingMember = memberRepository.findByEmail(email);

        if (optionalExistingMember.isPresent()) {
            Member existingMember = optionalExistingMember.get();
            existingMember.setMemberRole(provider);

            return memberRepository.save(existingMember);
        } else {
            Member member = Member.builder()
                    .email(email)
                    .role(Role.ROLE_NEW_USER)
                    .build();

            return memberRepository.save(member);
        }
    }

    public String saveRefresh(String email, String refreshToken) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();

        member.setRefreshToken(refreshToken);

        memberRepository.save(member);

        return member.getRefreshToken();
    }


}
