package com.knu.salmon.api.domain.member.service;

import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.type.Role;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional

public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveOrUpdate(String email, String provider) {
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

