package com.example.dolearn.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("jwtTokenProvider 로딩 성공 테스트")
    @Test
    public void JwtTokenProviderLoadingTest() {
        assertThat(jwtTokenProvider).isNotNull();
    }

    @DisplayName("리프레시 토큰 생성 테스트")
    @Test
    public void createRefreshTokenTest() {

        String email = "cksgnlcjswoo@naver.com";

        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        assertThat(refreshToken).isNotNull();
    }

    @DisplayName("리프레시 토큰 생성 테스트")
    @Test
    public void createAccessTokenTest() {

        String email = "cksgnlcjswoo@naver.com";

        String refreshToken = jwtTokenProvider.createRefreshToken(email);
        String accessToken = jwtTokenProvider.createAccessToken(refreshToken);

        assertThat(refreshToken).isNotNull();
        assertThat(accessToken).isNotNull();
    }

    @DisplayName("토큰에서 이메일 추출 테스트")
    @Test
    public void getUserPkTest() {
        String email = "cksgnlcjswoo@naver.com";

        String refreshToken = jwtTokenProvider.createRefreshToken(email);
        String accessToken = jwtTokenProvider.createAccessToken(refreshToken);

        String result = jwtTokenProvider.getUserPk(accessToken);

        assertThat(result).isEqualTo(email);
    }

    @DisplayName("토큰 검증 테스트")
    @Test
    public void validateTokenTest() {
        String email = "cksgnlcjswoo@naver.com";

        String refreshToken = jwtTokenProvider.createRefreshToken(email);
        String accessToken = jwtTokenProvider.createAccessToken(refreshToken);

        boolean result = jwtTokenProvider.validateToken(accessToken);

        assertThat(result).isEqualTo(true);
    }
}
