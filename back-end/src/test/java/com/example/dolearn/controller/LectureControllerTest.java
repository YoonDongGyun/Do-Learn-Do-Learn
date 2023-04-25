package com.example.dolearn.controller;

import com.example.dolearn.domain.Lecture;
import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserLecture;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.LectureDto;
import com.example.dolearn.dto.UserLectureDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.service.LectureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LectureController.class)
@WithMockUser
public class LectureControllerTest {
    @MockBean
    private LectureService lectureService;


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("강의 상세 테스트")
    @Test
    public void getLectureDetailTest() throws Exception{
        BoardDto board = BoardDto.builder().id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        Lecture lecture = Lecture.builder()
                .id(1L).board(board.toEntity()).memberCnt(0).build();

        when(lectureService.getDetail(any())).thenReturn(lecture.toMessageDto());

        mockMvc.perform(get("/api/lecture/{lecture_id}",lecture.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 상세 Exception 테스트")
    @Test
    public void getLectureDetailExceptionTest() throws Exception{
        BoardDto board = BoardDto.builder().id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        Lecture lecture = Lecture.builder()
                .id(1L).board(board.toEntity()).memberCnt(0).build();

        when(lectureService.getDetail(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/lecture/{lecture_id}",lecture.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 확정 테스트")
    @Test
    public void FixUpdateTest() throws Exception{
        Map<String,Long> data = new HashMap<>();

        data.put("bid",1L);
        data.put("Luid",1L);

        BoardDto board = BoardDto.builder().id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        Lecture lecture = Lecture.builder()
                .id(1L).board(board.toEntity()).memberCnt(0).build();

        when(lectureService.updateFix(any(),any())).thenReturn(lecture.toDto());

        mockMvc.perform(post("/api/lecture/fix",board.getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 확정 오류 테스트")
    @Test
    public void FixUpdateErrorTest() throws Exception{
        Map<String,Long> data = new HashMap<>();

        data.put("bid",1L);
        data.put("Luid",1L);

        when(lectureService.updateFix(any(),any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(post("/api/lecture/fix").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 확정 Exception 테스트")
    @Test
    public void FixUpdateExceptionTest() throws Exception{
        Map<String,Long> data = new HashMap<>();

        data.put("bid",1L);
        data.put("Luid",1L);

        when(lectureService.updateFix(any(),any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/lecture/fix").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강사 id 가져오기 테스트")
    @Test
    public void getInstructorTest() throws Exception {
        Lecture lecture = Lecture.builder()
                .id(1L).memberCnt(0).build();

        User user = User.builder().id(1L).name("test").build();

        UserLecture userLecture = UserLecture.builder()
                .id(1L).user(user).lecture(lecture).memberType("강사").build();


        when(lectureService.getInstructor(any())).thenReturn(user.getId());

        mockMvc.perform(get("/api/lecture/instructor/{Luid}",user.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강사 id 가져오기 오류 테스트")
    @Test
    public void getInstructorErrorTest() throws Exception {

        when(lectureService.getInstructor(any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(get("/api/lecture/instructor/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강사 id 가져오기 Exception 테스트")
    @Test
    public void getInstructorExceptionTest() throws Exception {

        when(lectureService.getInstructor(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/lecture/instructor/1"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 신청자 목록 가져오기 테스트")
    @Test
    public void getListTest() throws Exception{
        List<UserLecture> userLectureList = new ArrayList<>();

        Lecture lecture = Lecture.builder()
                .id(1L).memberCnt(0).build();

        UserLecture userLecture1 = UserLecture.builder()
                .lecture(lecture).memberType("학생").build();

        UserLecture userLecture2 = UserLecture.builder()
                .lecture(lecture).memberType("강사").build();

        userLectureList.add(userLecture1);
        userLectureList.add(userLecture2);

        when(lectureService.getList(any())).thenReturn(userLectureList);

        mockMvc.perform(get("/api/lecture/list/{lecture_id}",lecture.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 신청자 목록 가져오기 오류 테스트")
    @Test
    public void getListErrorTest() throws Exception{
        when(lectureService.getList(any())).thenThrow(new CustomException(ErrorCode.NO_APPLICANT));

        mockMvc.perform(get("/api/lecture/list/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 신청자 목록 가져오기 Exception 테스트")
    @Test
    public void getListExceptionTest() throws Exception{
        when(lectureService.getList(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/lecture/list/1"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 업데이트 테스트")
    @Test
    public void updateLectureTest() throws Exception{
        LectureDto lectureDto = LectureDto.builder()
                .id(1L).memberCnt(0).endRealTime(null).startRealTime(null).build();

        when(lectureService.updateLecture(any())).thenReturn(lectureDto);

        mockMvc.perform(put("/api/lecture").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(lectureDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 업데이트 오류 테스트")
    @Test
    public void updateLectureErrorTest() throws Exception{
        LectureDto lectureDto = LectureDto.builder()
                .id(1L).memberCnt(0).endRealTime(null).startRealTime(null).build();

        when(lectureService.updateLecture(any())).thenThrow(new CustomException(ErrorCode.NO_LECTURE));

        mockMvc.perform(put("/api/lecture").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lectureDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 업데이트 오류 테스트")
    @Test
    public void updateLectureExceptionTest() throws Exception{
        LectureDto lectureDto = LectureDto.builder()
                .id(1L).memberCnt(0).endRealTime(null).startRealTime(null).build();

        when(lectureService.updateLecture(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/lecture").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lectureDto)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 평가 여부 업데이트 테스트")
    @Test
    public void updateMemberLectureTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        info.put("lid",1L);
        info.put("uid",1L);

        UserLectureDto userLectureDto = UserLectureDto.builder()
                .evaluateStatus(0).memberType("학생").build();

        when(lectureService.updateLectureMember(any(),any())).thenReturn(userLectureDto);

        mockMvc.perform(put("/api/lecture/member-update").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 평가 여부 업데이트 오류 테스트")
    @Test
    public void updateMemberLectureErrorTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        when(lectureService.updateLectureMember(any(),any())).thenThrow(new CustomException(ErrorCode.NO_APPLICANT));

        mockMvc.perform(put("/api/lecture/member-update").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 평가 여부 업데이트 오류 테스트")
    @Test
    public void updateMemberLectureExceptionTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        when(lectureService.updateLectureMember(any(),any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/lecture/member-update").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 취소 테스트")
    @Test
    public void cancelApplyTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        info.put("lid",1L);
        info.put("uid",1L);

        when(lectureService.cancelApply(info.get("lid"),info.get("uid"))).thenReturn(1);

        mockMvc.perform(delete("/api/lecture/apply").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 취소 오류 테스트")
    @Test
    public void cancelApplyErrorTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        info.put("lid",1L);
        info.put("uid",1L);

        when(lectureService.cancelApply(info.get("lid"),info.get("uid"))).thenThrow(new CustomException(ErrorCode.NO_LECTURE));

        mockMvc.perform(delete("/api/lecture/apply").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 취소 오류 테스트")
    @Test
    public void cancelApplyExceptionTest() throws Exception{
        Map<String,Long> info = new HashMap<>();

        info.put("lid",1L);
        info.put("uid",1L);

        when(lectureService.cancelApply(info.get("lid"),info.get("uid"))).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/api/lecture/apply").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(info)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}
