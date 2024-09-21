package com.knu.salmon.api.auth.oauth2.service;

import com.knu.salmon.api.auth.oauth2.dto.response.GoogleResponse;
import com.knu.salmon.api.auth.oauth2.dto.response.KakaoResponse;
import com.knu.salmon.api.auth.oauth2.dto.response.Oauth2Response;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final AuthService authService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("registrationId : {}", registrationId);

        Oauth2Response oauth2Response = getOauth2Response(registrationId, oAuth2User);

        log.info("oauth2Reponse email : {}", oauth2Response.getEmail());
        log.info("oauth2Reponse name : {}", oauth2Response.getName());
        log.info("oauth2Reponse provider : {}", oauth2Response.getProvider());
        log.info("oauth2Reponse providerId : {}", oauth2Response.getProviderId());

        Member member = authService.oauth2SaveOrUpdate(oauth2Response.getEmail(), oauth2Response.getProvider());

        PrincipalDetails principalDetails = new PrincipalDetails(member, oAuth2User.getAttributes());

        return principalDetails;
    }

    private static Oauth2Response getOauth2Response(String registrationId, OAuth2User oAuth2User) {
        if (registrationId.equals("google")) {
            return new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {

            log.info("attributes : {}", oAuth2User.getAttributes());

            return new KakaoResponse(oAuth2User.getAttributes());
        }
        return null;
    }
}
