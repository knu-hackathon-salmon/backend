package com.knu.salmon.api.auth.oauth2.dto.request;

import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.type.Role;
import lombok.Builder;

@Builder
public record Oauth2Dto(String email, String role) {

    public Member oauth2DtoToMember(Oauth2Dto oauth2Dto) {
        return Member.builder()
                .email(oauth2Dto.email())
                .role(Role.valueOf(oauth2Dto.role()))
                .build();
    }

}
