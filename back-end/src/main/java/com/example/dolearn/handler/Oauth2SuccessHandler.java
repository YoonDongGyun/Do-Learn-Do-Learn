package com.example.dolearn.handler;

import com.example.dolearn.domain.User;
import com.example.dolearn.dto.OAuthAttributes;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Configuration
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    public Oauth2SuccessHandler(JwtTokenProvider jwtTokenProvider,
                                UserRepository userRepository
                                ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuthAttributes oAuthAttributes = (OAuthAttributes) authentication.getPrincipal();

        String refreshToken = jwtTokenProvider.createRefreshToken(oAuthAttributes.getEmail());
        String accessToken = jwtTokenProvider.createAccessToken(refreshToken);

        Optional<User> user = userRepository.findOneByEmail(oAuthAttributes.getEmail());
        //user가 존재한다면 토큰 갱신
        if(user.isPresent()) {
            user.get().updateRefreshToken(refreshToken);
            userRepository.save(user.get());
        }
        Long userId = user.get().getId();
        log.info("social login user id {} :",userId);

        StringBuilder sb = new StringBuilder();
        sb.append("https://i8a802.p.ssafy.io").append("/oauth-redirect?refreshToken=")
                .append(refreshToken)
                .append("&&accessToken=")
                .append(accessToken)
                .append("&&userId=")
                .append(userId);

        response.sendRedirect(sb.toString());



    }
}
