package com.example.dolearn.dto;

import com.example.dolearn.config.SecurityConfig;
import com.example.dolearn.controller.UserController;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserDtoTest {

    @Nested
    @WebMvcTest(value = UserController.class,
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            }
    )
    class ControllerValidation {
        MockMvc mockMvc;

        @MockBean
        UserService userService;

        @MockBean
        JwtTokenProvider jwtTokenProvider;

        @Autowired
        WebApplicationContext context;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .alwaysDo(print())
                    .build();
        }

        @Test
        @DisplayName("DTO 유효성 체크 성공")
        public void success() throws Exception {
            UserDto userDto = UserDto.builder().email("ssafy@naver.com").name("민싸피").password("abcd!1234").build();

            when(userService.signup(any(UserDto.class))).thenReturn(userDto);

            mockMvc.perform(post("/api/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                            .content(toJson(userDto)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("DTO 유효성 체크 실패 - 유효하지 않은 이메일 형식")
        public void failByEmail() throws Exception {
            UserDto userDto = UserDto.builder().email("이상한 이메일").name("민싸피").password("abcd!1234").build();

            mockMvc.perform(post("/api/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                            .content(toJson(userDto)))
                    .andExpect(
                            (res)-> assertTrue(res.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class))
                    );
        }

        private String toJson(Object object) {
            try {
                return new ObjectMapper().writeValueAsString(object);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}