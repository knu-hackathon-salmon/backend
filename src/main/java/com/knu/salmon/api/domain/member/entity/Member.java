package com.knu.salmon.api.domain.member.entity;

import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.entity.type.Role;
import com.knu.salmon.api.domain.seller.entity.Shop;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @OneToOne(mappedBy = "member")
    private Shop shop;

    @OneToOne(mappedBy = "member")
    private Customer customer;

    @Builder
    public Member(String email, Role role, String refreshToken, MemberType memberType) {
        this.email = email;
        this.role = role;
        this.refreshToken = refreshToken;
        this.memberType = memberType;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void setMemberRole(String provider){
        if(provider.equals("google")){
            this.role = Role.ROLE_GOOGLE_USER;
        }

        if(provider.equals("kakao")){
            this.role = Role.ROLE_KAKAO_USER;
        }

        if(!provider.equals("google") && provider.equals("kakao")){
            this.role = Role.ROLE_NEW_USER;
        }
    }

    public void updateMember(Member member) {
        if (member.getEmail() != null) {
            this.email = member.getEmail();
        }

        if (member.getRole() != null) {
            this.role = member.getRole();
        }
    }
}
