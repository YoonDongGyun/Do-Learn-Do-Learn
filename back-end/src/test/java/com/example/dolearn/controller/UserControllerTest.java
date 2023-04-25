package com.example.dolearn.controller;

import com.example.dolearn.config.SecurityConfig;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.FixedLectureDto;
import com.example.dolearn.dto.SummaryUserDto;
import com.example.dolearn.dto.UserDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = UserController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)
public class UserControllerTest {

    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    WebApplicationContext context;

    @Value("${file.path}")
    String filePath;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }


    @Test
    void 회원가입성공() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.signup(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(toJson(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입실패_이메일중복() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.signup(any(UserDto.class))).thenThrow(new CustomException(ErrorCode.EMAIL_DUPLICATION));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(userDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void 회원가입실패_서버에러() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.signup(any(UserDto.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(userDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 로그인성공() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.login(any(UserDto.class))).thenReturn(userDto);
        when(jwtTokenProvider.createRefreshToken(any(String.class))).thenReturn("refresh-token");
        when(jwtTokenProvider.createAccessToken(any(String.class))).thenReturn("access-token");
        when(userService.updateToken(any(UserDto.class), any(String.class), any(String.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(toJson(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 이메일")
    void 로그인실패_존재하지않는이메일() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.login(any(UserDto.class))).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 로그인실패_비번오류() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.login(any(UserDto.class))).thenThrow(new CustomException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 로그인실패_서버에러() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.login(any(UserDto.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(userDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 접근토큰재발급성공() throws Exception {

        when(jwtTokenProvider.createAccessToken(any(String.class))).thenReturn("ABSLDKJALSKJFDSALKFDJASKFDLJS");

        mockMvc.perform(post("/api/user/access")
                        .header("Authentication", "SDSKLDJFALSKJDFASLKDFJS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 접근토큰재발급실패() throws Exception {

        when(jwtTokenProvider.createAccessToken(any(String.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/user/access")
                        .header("Authentication", "SDSKLDJFALSKJDFASLKDFJS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void 로그아웃성공() throws Exception {
        when(userService.logout(1L)).thenReturn(any(UserDto.class));

        mockMvc.perform(post("/api/user/logout/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 로그아웃실패_없는사용자() throws Exception {
        when(userService.logout(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(post("/api/user/logout/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 사용자정보수정성공() throws Exception {
        MockMultipartFile imgSrc = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
        UserDto userDto = UserDto.builder().build();

        when(userService.getInfo(any(Long.class))).thenReturn(userDto);
        when(userService.updateInfo(any(UserDto.class))).thenReturn(userDto);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/user");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder.file(imgSrc)
                        .content(toJson(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자정보수정실패_부족한정보() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.updateInfo(any(UserDto.class))).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(put("/api/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 사용자정보수정실패_없는사용자() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.updateInfo(any(UserDto.class))).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(put("/api/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 사용자정보수정실패_서버에러() throws Exception {
        UserDto userDto = UserDto.builder().build();

        when(userService.updateInfo(any(UserDto.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isInternalServerError());
    }

//    @Test
//    void 프로필사진업로드성공_기본이미지초기화() throws Exception {
//        UserDto userDto = UserDto.builder().imgUrl("imgUrl").imgPath("imgPath").build();
//        MockMultipartFile image = new MockMultipartFile("files", "originalName.jpeg", "image/jpeg", new FileInputStream(filePath+"1470246520943804.jpg"));
//
//        when(userService.getInfo(1L)).thenReturn(userDto);
//
//        mockMvc.perform(multipart("/api/user/upload-img/1")
//                        .file(image)
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void 프로필사진업로드성공() throws Exception {
//        UserDto userDto = UserDto.builder().imgUrl("imgUrl").imgPath("imgPath").build();
//        MockMultipartFile profileImg = new MockMultipartFile("profileImg", "originalName.jpeg", "image/jpeg", new FileInputStream(filePath+"1470246520943804.jpg"));
//
//        when(userService.getInfo(1L)).thenReturn(userDto);
//        when(userService.updateImgInfo(1L, "path", "imgUrl")).thenReturn(userDto);
//
//        mockMvc.perform(multipart("/api/user/upload-img/1")
//                        .file(profileImg)
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void 프로필사진업로드실패() throws Exception {
//        MockMultipartFile profileImg = new MockMultipartFile("profileImg", "originalName.jpeg", "image/jpeg", new FileInputStream(filePath+"1470246520943804.jpg"));
//
//        when(userService.getInfo(1L)).thenThrow(new RuntimeException());
//
//        mockMvc.perform(multipart("/api/user/upload-img/1")
//                        .file(profileImg)
//                        .with(csrf()))
//                .andExpect(status().isInternalServerError());
//    }

    @Test
    void 중복되지않은이메일() throws Exception {
        mockMvc.perform(post("/api/user/check-email/ssafy@naver.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("중복된 이메일")
    void 중복된이메일() throws Exception {
        doThrow(new CustomException(ErrorCode.EMAIL_DUPLICATION)).when(userService).checkEmail(any(String.class));

        mockMvc.perform(post("/api/user/check-email/ssafy@naver.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict());
    }

    @Test
    void 사용자정보조회성공() throws Exception {
        when(userService.getInfo(1L)).thenReturn(any(UserDto.class));

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자정보조회실패_없는사용자() throws Exception {
        when(userService.getInfo(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 전체사용자ID조회성공() throws Exception {
        List<Long> id = new ArrayList<>();
        id.add(1L);
        id.add(2L);

        when(userService.getAllId()).thenReturn(id);

        mockMvc.perform(get("/api/user/all-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 전체사용자ID조회실패() throws Exception {
        when(userService.getAllId()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/all-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 사용자프로필정보조회성공() throws Exception {
        when(userService.getSummaryInfo(1L)).thenReturn(any(SummaryUserDto.class));

        mockMvc.perform(get("/api/user/summary-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자프로필정보조회성공_없는사용자() throws Exception {
        when(userService.getSummaryInfo(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/summary-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 사용자삭제성공() throws Exception {
        mockMvc.perform(delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자삭제실패_없는사용자() throws Exception {
        doThrow(new CustomException(ErrorCode.NO_USER)).when(userService).delete(any(Long.class));

        mockMvc.perform(delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }


    @Test
    void 미확정강의조회성공() throws Exception {
        when(userService.getRequestLecture(1L)).thenReturn((List<BoardDto>) any(BoardDto.class));

        mockMvc.perform(get("/api/user/request-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 미확정강의조회실패_부족한정보() throws Exception {
        when(userService.getRequestLecture(1L)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(get("/api/user/request-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 미확정강의조회실패_없는사용자() throws Exception {
        when(userService.getRequestLecture(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/request-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 미확정강의조회실패_서버에러() throws Exception {
        when(userService.getRequestLecture(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/request-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 미확정강의_방장_조회성공() throws Exception {
        when(userService.getRequestLectureByHost(1L)).thenReturn((List<BoardDto>) any(BoardDto.class));

        mockMvc.perform(get("/api/user/request-lecture/1/host")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 미확정강의_방장_조회실패_부족한정보() throws Exception {
        when(userService.getRequestLectureByHost(1L)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(get("/api/user/request-lecture/1/host")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 미확정강의조회실패_방장_없는사용자() throws Exception {
        when(userService.getRequestLectureByHost(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/request-lecture/1/host")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 미확정강의조회실패_방장_서버에러() throws Exception {
        when(userService.getRequestLectureByHost(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/request-lecture/1/host")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 미확정강의_강사_조회성공() throws Exception {
        when(userService.getRequestLectureByInst(1L)).thenReturn((List<BoardDto>) any(BoardDto.class));

        mockMvc.perform(get("/api/user/request-lecture/1/instructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 미확정강의_강사_조회실패_부족한정보() throws Exception {
        when(userService.getRequestLectureByInst(1L)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(get("/api/user/request-lecture/1/instructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 미확정강의조회실패_강사_없는사용자() throws Exception {
        when(userService.getRequestLectureByInst(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/request-lecture/1/instructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 미확정강의조회실패_강사_서버에러() throws Exception {
        when(userService.getRequestLectureByInst(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/request-lecture/1/instructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 미확정강의_학생_조회성공() throws Exception {
        when(userService.getRequestLectureByStud(1L)).thenReturn((List<BoardDto>) any(BoardDto.class));

        mockMvc.perform(get("/api/user/request-lecture/1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 미확정강의조회실패_학생_부족한정보() throws Exception {
        when(userService.getRequestLectureByStud(1L)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(get("/api/user/request-lecture/1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 미확정강의조회실패_학생_없는사용자() throws Exception {
        when(userService.getRequestLectureByStud(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/request-lecture/1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 미확정강의조회실패_학생_서버에러() throws Exception {
        when(userService.getRequestLectureByStud(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/request-lecture/1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 확정강의조회성공() throws Exception {
        when(userService.getFixedLecture(1L)).thenReturn((List<FixedLectureDto>) any(BoardDto.class));

        mockMvc.perform(get("/api/user/fixed-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 확정강의조회실패_학생_부족한정보() throws Exception {
        when(userService.getFixedLecture(1L)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(get("/api/user/fixed-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 확정강의조회실패_없는사용자() throws Exception {
        when(userService.getFixedLecture(1L)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(get("/api/user/fixed-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 확정강의조회실패_서버에러() throws Exception {
        when(userService.getFixedLecture(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/user/fixed-lecture/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 사용자포인트수정성공() throws Exception {
        Map<String, Object> params = new HashMap<>();

        when(userService.updatePoint(params)).thenReturn(any(UserDto.class));

        mockMvc.perform(put("/api/user/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(params)))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자포인트수정실패_부족한정보() throws Exception {
        Map<String, Object> params = new HashMap<>();

        when(userService.updatePoint(params)).thenThrow(new CustomException(ErrorCode.INVALID_INPUT));

        mockMvc.perform(put("/api/user/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(params)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void 사용자포인트수정실패_없는사용자() throws Exception {
        Map<String, Object> params = new HashMap<>();

        when(userService.updatePoint(params)).thenThrow(new CustomException(ErrorCode.NO_USER));

        mockMvc.perform(put("/api/user/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 사용자포인트수정실패_서버에러() throws Exception {
        Map<String, Object> params = new HashMap<>();

        when(userService.updatePoint(params)).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/user/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(params)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 랭킹조회성공() throws Exception {
        when(userService.getAllSortByPoint()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/user/sort-point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 랭킹조회실패() throws Exception {
        when(userService.getAllSortByPoint()).thenThrow(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));

        mockMvc.perform(get("/api/user/sort-point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }
    
    private static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
