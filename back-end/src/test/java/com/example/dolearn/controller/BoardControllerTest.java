package com.example.dolearn.controller;

import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.UserBoard;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.UserBoardDto;
import com.example.dolearn.dto.UserDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.service.BoardService;
import com.example.dolearn.service.UserBoardService;
import com.example.dolearn.service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BoardController.class)
@WithMockUser
public class BoardControllerTest {

    @MockBean
    private BoardService boardService;

    @MockBean
    private UserBoardService userBoardService;

    @MockBean
    private UserService userService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    BoardDto boardDto1 = BoardDto.builder()
            .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    BoardDto boardDto2 = BoardDto.builder()
            .id(2L).uid(2L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    @DisplayName("글 생성 테스트")
    @Test
    public void insertBoardTest() throws Exception{
        BoardDto boardDto = BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        when(boardService.insert(any())).thenReturn(boardDto);

        mockMvc.perform(post("/api/board").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(boardDto.toEntity())))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @DisplayName("글 생성 오류 테스트")
    @Test
    public void insertBoardExceptionTest() throws Exception {
        BoardDto boardDto = BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        when(boardService.insert(any())).thenThrow(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));

        mockMvc.perform(post("/api/board").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(boardDto.toEntity())))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 요청 게시물 불러오기 테스트")
    @Test
    public void boardListTest() throws Exception{
        List<BoardDto> boardList = new ArrayList<>();

        boardList.add(boardDto1);
        boardList.add(boardDto2);

        when(boardService.selectAll()).thenReturn(boardList);

        mockMvc.perform(get("/api/board/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 게시물 불러오기 오류 테스트")
    @Test
    public void noBoardListTest() throws Exception{
        when(boardService.selectAll()).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(get("/api/board/list"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 게시물 불러오기 Exception 테스트")
    @Test
    public void BoardListExceptionTest() throws Exception{
        when(boardService.selectAll()).thenThrow(new Exception());

        mockMvc.perform(get("/api/board/list"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("글 상세보기")
    @Test
    public void boardDetailTest() throws Exception{

        when(boardService.selectDetail(any())).thenReturn(boardDto1);

        mockMvc.perform(get("/api/board/{board_id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("글이 없을 때 상세보기 테스트")
    @Test
    public void noBoardDetailTest() throws Exception{

        when(boardService.selectDetail(any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(get("/api/board/200"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("글 상세보기 Exception 테스트")
    @Test
    public void boardDetailExceptionTest() throws Exception{

        when(boardService.selectDetail(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/board/200"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("글 삭제 테스트")
    @Test
    public void boardDeleteTest() throws Exception{
        when(boardService.deleteBoard(any())).thenReturn(1);

        mockMvc.perform(delete("/api/board/{board_id}",2).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("글 삭제 오류 테스트")
    @Test
    public void boardDeleteErrorTest() throws Exception{

        when(boardService.deleteBoard(any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(delete("/api/board/{board_id}","11").with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("글 삭제 Exception 테스트")
    @Test
    public void boardDeleteExceptionTest() throws Exception{

        when(boardService.deleteBoard(any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/api/board/{board_id}","11").with(csrf()))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강사 목록")
    @Test
    public void getInstructorsTest() throws Exception{
        List<UserBoard> result = new ArrayList<>();

        UserDto userDto = UserDto.builder().name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("강사").build();

        result.add(userBoardDto.toEntity());

        when(userBoardService.getInstructors(any())).thenReturn(result);

        mockMvc.perform(get("/api/board/instructor-list/{board_id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강사가 없는 경우 목록 테스트")
    @Test
    public void getNoInstructorsTest() throws Exception{

        mockMvc.perform(get("/api/board/instructor-list/{board_id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강사가 없는 경우 Exception 테스트")
    @Test
    public void noinstructorsest() throws Exception{

        mockMvc.perform(get("/api/board/instructor-list/{board_id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강사가 없는 경우 Exception 테스트")
    @Test
    public void instructorsExceptionTest() throws Exception{
        when(userBoardService.getInstructors(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/board/instructor-list/{board_id}",1))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("학생 목록")
    @Test
    public void getStudentsTest() throws Exception{
        List<UserBoard> result = new ArrayList<>();

        UserDto userDto = UserDto.builder().name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        result.add(userBoardDto.toEntity());

        when(userBoardService.getStudents(any())).thenReturn(result);

        mockMvc.perform(get("/api/board/student-list/{board_id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("학생 목록 오류 테스트")
    @Test
    public void getStudentsErrorTest() throws Exception{
        when(userBoardService.getStudents(any())).thenThrow(new CustomException(ErrorCode.NO_STUDENTS));

        mockMvc.perform(get("/api/board/student-list/{board_id}",1))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("학생 목록 Exception 테스트")
    @Test
    public void getStudentsExceptionTest() throws Exception{
        when(userBoardService.getStudents(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/board/student-list/{board_id}",1))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 검색")
    @Test
    public void searchBoardTest() throws Exception{
        List<Board> result = new ArrayList<>();

        result.add(boardDto1.toEntity());
        result.add(boardDto2.toEntity());

        when(boardService.searchBoardContent(any())).thenReturn(result);
        when(boardService.searchBoardTitle(any())).thenReturn(result);


        mockMvc.perform(get("/api/board/search/{keyword}","title"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 검색 오류 테스트")
    @Test
    public void searchBoardErrorTest() throws Exception{

        when(boardService.searchBoardContent(any())).thenThrow(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        when(boardService.searchBoardTitle(any())).thenThrow(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));


        mockMvc.perform(get("/api/board/search/{keyword}","1"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 검색 Exception 테스트")
    @Test
    public void searchBoardExceptionTest() throws Exception{

        when(boardService.searchBoardContent(any())).thenThrow(new RuntimeException());
        when(boardService.searchBoardTitle(any())).thenThrow(new RuntimeException());


        mockMvc.perform(get("/api/board/search/{keyword}","1"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("수강 신청")
    @Test
    public void applyStudentTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).bid(1L).uid(1L).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        when(userService.signup(any())).thenReturn(userDto);
        when(boardService.insert(any())).thenReturn(boardDto1);

        when(userBoardService.applyClass(any())).thenReturn(userBoardDto);

        data.put("uid",1L);
        data.put("bid",1L);

        mockMvc.perform(post("/api/board/student").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("수강 신청 정원 초과 테스트")
    @Test
    public void applyStudentOverTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        when(userBoardService.applyClass(any())).thenThrow(new CustomException(ErrorCode.EXEED_STUDENTS));

        mockMvc.perform(post("/api/board/student").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("수강 신청 오류 테스트")
    @Test
    public void applyStudentExceptionTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        mockMvc.perform(post("/api/board/student").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강사 신청")
    @Test
    public void applyInstructorTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).bid(1L).uid(1L).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("강사").build();

        when(userService.signup(any())).thenReturn(userDto);
        when(boardService.insert(any())).thenReturn(boardDto1);

        when(userBoardService.applyClass(any())).thenReturn(userBoardDto);

        data.put("uid",1L);
        data.put("bid",1L);
        mockMvc.perform(post("/api/board/instructor").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강사 신청 오류 테스트")
    @Test
    public void applyInstructorErrorTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        when(userBoardService.applyClass(any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(post("/api/board/instructor").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강사 신청 Exception 테스트")
    @Test
    public void applyInstructorExceptionTest() throws Exception{
        Map<String,Long> data =new HashMap<>();

        when(userBoardService.applyClass(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/board/instructor").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("강의 신청 취소")
    @Test
    public void cancelApplyTest() throws Exception{
        Map<String,Long> data = new HashMap<>();

        data.put("uid",1L);
        data.put("bid",1L);

        when(userBoardService.cancelApply(any(),any())).thenReturn(1);

        mockMvc.perform(delete("/api/board/apply/{uid}/{bid}",data.get("uid"),data.get("bid")).with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 신청 취소 오류")
    @Test
    public void cancelApplyErrorTest() throws Exception{

        when(userBoardService.cancelApply(any(),any())).thenThrow(new CustomException(ErrorCode.NO_BOARD));

        mockMvc.perform(delete("/api/board/apply/{uid}/{bid}","1","2").with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("강의 신청 취소 Exception")
    @Test
    public void cancelApplyExceptionTest() throws Exception{

        when(userBoardService.cancelApply(any(),any())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/api/board/apply/{uid}/{bid}","1","2").with(csrf()))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}
