package com.example.dolearn.jwt;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// UsernamePasswordAuthenticationFilter : login 시 username(email), password를 post 전송하면 동작
@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private JwtTokenProvider jwtTokenProvider;

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // request header에서 토큰 획득
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰 인증과정을 거친 결과를 authentication이라는 이름으로 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체 저장
            // token이 인증된 상태를 유지하도록 context(맥락)을 유지
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.info("토큰 유효성 검사 실패 : JwtAuthenticationFilter > doFilter");
        }

        //UsernamePasswordAuthenticationFilter로 이동
        chain.doFilter(request, response);
    }

}
