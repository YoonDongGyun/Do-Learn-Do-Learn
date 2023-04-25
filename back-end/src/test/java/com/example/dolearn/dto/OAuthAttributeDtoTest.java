package com.example.dolearn.dto;

import com.example.dolearn.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.server.HandshakeHandler;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OAuthAttributeDtoTest {

    @DisplayName("구글 OAuthAttribute 생성 테스트")
    @Test
    public void OAuthAttributeGoogleTest() {

        Map<String,Object> attributes = new HashMap<>();
        attributes.put("email","cksgnlcjswoo@naver.com");
        attributes.put("name","karin kim");

        OAuthAttributes google = OAuthAttributes
                .of("google","test1234",attributes);

        assertThat(google.getName()).isEqualTo("karin kim");
        assertThat(google.getEmail()).isEqualTo("cksgnlcjswoo@naver.com");
    }

    @DisplayName("카카오 OAuthAttribute 생성 테스트")
    @Test
    public void OAuthAttributeKaKaoTest() {

        Map<String,Object> attributes = new HashMap<>();

        Map<String,Object> profile = new HashMap<>();
        Map<String,Object> response = new HashMap<>();

        profile.put("nickname","cksgnlcjswo");
        response.put("profile",profile);
        response.put("email","cksgnlcjswoo@naver.com");
        attributes.put("kakao_account",response);

        OAuthAttributes kakao = OAuthAttributes
                .of("kakao","test1234",attributes);

        assertThat(kakao.getName()).isEqualTo("cksgnlcjswo");
        assertThat(kakao.getEmail()).isEqualTo("cksgnlcjswoo@naver.com");
    }

    @DisplayName("OauthAttribute to user entity")
    @Test
    public void OAuthAttributeToUserTest() {

        Map<String,Object> attributes = new HashMap<>();
        attributes.put("email","cksgnlcjswoo@naver.com");
        attributes.put("name","karin kim");

        OAuthAttributes google = OAuthAttributes
                .of("google","test1234",attributes);

        User user = google.toEntity();

        assertThat(user.getEmail()).isEqualTo("cksgnlcjswoo@naver.com");
        assertThat(user.getName()).isEqualTo("karin kim");
    }
}
