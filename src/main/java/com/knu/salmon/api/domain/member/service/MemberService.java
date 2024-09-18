package com.knu.salmon.api.domain.member.service;

import com.knu.salmon.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberService {

    private final MemberRepository memberRepository;

}

