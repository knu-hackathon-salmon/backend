package com.knu.salmon.api.auth.jwt.filter;

import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.Role;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.member.service.MemberService;
import com.knu.salmon.api.global.error.custom.JwtTokenException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.JwtTokenErrorCode;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // filter를 거치고 싶지 않은 path를 여기서 관리함
        String[] excludePathLists = {"/favicon.ico", "/swagger-ui/index.html", "/api/jwt/reissue", "/api/member/oauth2/temp"};
        String[] excludePathStartsWithLists = {"/login", "/oauth2", "/v3", "/swagger-ui", "/ws", "/api/oauth2","/api/food/detail", "/api/food/overview"};

        String path = request.getRequestURI();

        // 해당 경로로 시작하는 uri에 대해서는 true를 반환하고 filter를 거치지 않음
        boolean startsWithExcludedPath = Arrays.stream(excludePathStartsWithLists).
                anyMatch(path::startsWith);

        // excludePathLists과 같은 uri로 매칭되면 true를 반환하고 filter를 거치지않음.
        boolean matchesExcludedPath = Arrays.stream(excludePathLists)
                .anyMatch((excludePath) -> excludePath.equals(path));

        return startsWithExcludedPath || matchesExcludedPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // 요청 헤더 출력
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {} = {}", headerName, headerValue);
        }

        // 요청 바디 출력 (InputStream으로 읽기)
        StringBuilder body = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        log.info("Request Body: {}", body.toString());

        // Authorization 헤더 처리
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new JwtTokenException(JwtTokenErrorCode.NO_EXIST_AUTHORIZATION_HEADER_EXCEPTION);
        }

        String accessToken = authorizationHeader.substring(7);

        if (jwtService.validateToken(accessToken)) {
            String email = jwtService.getEmail(accessToken);
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

            // 인증 처리
            authentication(request, response, filterChain, member);
            return;
        }

        filterChain.doFilter(request, response);
    }



    /**
     * 유저를 authentication 해주는 메소드
     */
    private static void authentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Member member) throws IOException, ServletException {
        // PrincipalDetails에 유저 정보를 담기
        PrincipalDetails principalDetails = new PrincipalDetails(member);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authenticationToken
                = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        // 세션에 사용자 등록, 해당 사용자는 스프링 시큐리티에 의해서 인증됨
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        MDC.put("member_email", member.getEmail());

        filterChain.doFilter(request, response);
    }
}
