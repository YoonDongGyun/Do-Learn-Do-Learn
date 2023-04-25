package com.example.dolearn.config;

import com.example.dolearn.handler.Oauth2SuccessHandler;
import com.example.dolearn.jwt.CustomAuthenticationEntryPoint;
import com.example.dolearn.jwt.JwtAuthenticationFilter;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.repository.UserRepository;
import com.example.dolearn.service.CustomOauth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomOauth2UserService customOauth2UserService;

    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // jwt 사용을 위한 기본 설정
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers("/user/login").permitAll()             // jwt 인증 제외할 url 설정
//                .antMatchers("/user/check-email/**").permitAll()
//                .antMatchers("/exception/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/user").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()

                // login 시 Jwt 검증 필터
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

                //Jwt 토큰 인증 과정 중 에러 처리를 위한 Entry point 등록
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                // cors 설정 적용
                .and()
                .cors().configurationSource(corsConfigurationSource())

                //oauth2 관련
                .and()
                .oauth2Login()
                .successHandler(new Oauth2SuccessHandler(jwtTokenProvider,userRepository))
                .userInfoEndpoint()
                .userService(customOauth2UserService);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);       // 서버의 json 응답을 JS로 처리가능하게 함
        config.addAllowedOriginPattern("*");    // springboot cors 설정 시, allowCredentials(true)와 allowedOrigin("*") 같이 사용 불가하게 업뎃
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }
}

