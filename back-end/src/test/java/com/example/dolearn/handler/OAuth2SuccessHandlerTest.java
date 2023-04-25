package com.example.dolearn.handler;

import com.example.dolearn.domain.User;
import com.example.dolearn.dto.OAuthAttributes;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OAuth2SuccessHandlerTest {

    Oauth2SuccessHandler handler;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        handler = new Oauth2SuccessHandler(jwtTokenProvider,userRepository);
    }

    @DisplayName("소셜로그인 성공 후 리다이렉션 테스트")
    @Test
    public void googleSocialLoginTest() throws Exception {

        User user = User
                .builder()
                .email("tmp@naver.com")
                .name("testUser")
                .build();

        when(jwtTokenProvider.createRefreshToken(anyString())).thenReturn("tmpRefreshToken");
        when(jwtTokenProvider.createAccessToken(anyString())).thenReturn("tmpAccessToken");
        when(userRepository.findOneByEmail(anyString())).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        OAuthAttributes oAuthAttributes = OAuthAttributes
                .builder()
                .email("tmp@naver.com")
                .name("testUser")
                .build();

        handler.onAuthenticationSuccess(request,response,oAuthAttributes);
        assertThat(response.getRedirectedUrl()).contains("/oauth-redirect");
    }
}
