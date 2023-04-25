package com.example.dolearn.service;

import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserBoard;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.UserBoardDto;
import com.example.dolearn.dto.UserDto;
import com.example.dolearn.repository.BoardRepository;
import com.example.dolearn.repository.UserBoardRepository;
import com.example.dolearn.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BoardServiceTest {

    @InjectMocks
    BoardService boardService;

    @InjectMocks
    UserBoardService userBoardService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserBoardRepository userBoardRepository;

    @Mock
    UserRepository userRepository;

    private final BoardDto boardDto1 = BoardDto.builder()
            .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    private final BoardDto boardDto2 = BoardDto.builder()
            .id(2L).uid(2L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    @DisplayName("글 생성 테스트")
    @Test
    public void BoardCreateTest() throws Exception {
        UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();

        BoardDto boardDto3 = BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18")
                .startTime("2023-01-18 14").endTime("2")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build();

        when(boardRepository.save(any(Board.class))).thenReturn(boardDto1.toEntity());
        when(userRepository.findOneById(any())).thenReturn(Optional.ofNullable(userDto.toEntity()));

        Board result = boardService.insert(boardDto3).toEntity();

        assertEquals(boardDto1.getContent(),result.getContent());
    }

    @DisplayName("글 목록 전체 조회")
    @Test
    public void boardListTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        when(boardRepository.findAll()).thenReturn(boardList);

        List<BoardDto> result = boardService.selectAll();

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("글 상세 조회")
    @Test
    public void detailBoardTest() throws Exception {
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(boardDto1.toEntity()));

        BoardDto result = boardService.selectDetail(any());

        assertEquals(boardDto1.getId(),result.getId());
    }

    @DisplayName("글 삭제")
    @Test
    public void deleteBoardTest(){
        when(boardRepository.deleteBoard(any())).thenReturn(1);

        int result = boardService.deleteBoard(boardDto1.getId());

        assertEquals(1,result);
    }

    @DisplayName("제목으로 검색")
    @Test
    public void searchBoardTitleTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        when(boardRepository.findByTitleContaining(any())).thenReturn(boardList);

        List<Board> result = boardService.searchBoardTitle(any());

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("내용으로 검색")
    @Test
    public void searchBoardContentTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        when(boardRepository.findByContentContaining(any())).thenReturn(boardList);

        List<Board> result = boardService.searchBoardContent(any());

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("요약으로 검색")
    @Test
    public void searchBoardSummaryTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        when(boardRepository.findBySummaryContaining(any())).thenReturn(boardList);

        List<Board> result = boardService.searchBoardSummary(any());

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("검색 결과 종합")
    @Test
    public void searchResultTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        when(boardRepository.findByContentContaining(any())).thenReturn(boardList);
        when(boardRepository.findByTitleContaining(any())).thenReturn(boardList);
        when(boardRepository.findBySummaryContaining(any())).thenReturn(boardList);

        List<BoardDto> result = boardService.searchResult(boardList,boardList,boardList);

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("강사 목록 조회")
    @Test
    public void getInstructorsTest() throws Exception {
        List<UserBoard> userBoardList = new ArrayList<>();

        UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).bid(boardDto1.getId()).uid(userDto.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("강사").build();

        userBoardList.add(userBoardDto.toEntity());

        when(userBoardRepository.findInstructors(any())).thenReturn(userBoardList);

        List<UserBoard> result = userBoardService.getInstructors(boardDto1.getId());

        assertEquals(userBoardList.size(),result.size());
    }

    @DisplayName("학생 목록 조회")
    @Test
    public void getStudentsTest() throws Exception {
        List<UserBoard> userBoardList = new ArrayList<>();

        UserDto userDto = UserDto.builder().name("name").email("email").password("password").build();

        UserBoardDto userBoardDto = UserBoardDto.builder()
                .id(1L).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        userBoardList.add(userBoardDto.toEntity());

        when(userBoardRepository.findStudents(any())).thenReturn(userBoardList);

        List<UserBoard> result = userBoardService.getStudents(boardDto1.getId());

        assertEquals(userBoardList.size(),result.size());
    }

    @DisplayName("수강 신청")
    @Test
    public void applyClassTest() throws Exception{
        UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();
        Optional<User> user = Optional.of(userDto.toEntity());
        Optional<Board> board = Optional.of(boardDto1.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .id(1L).bid(boardDto1.getId()).uid(userDto.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("강사").build();

        when(userRepository.findOneById(any())).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(board);
        when(userBoardRepository.save(any())).thenReturn(userBoard);

        UserBoardDto result = userBoardService.applyClass(userBoard);

        assertEquals(userBoard.getUserType(),result.getUserType());

    }

    @DisplayName("수강 취소")
    @Test
    public void applyCancelTest() throws Exception{
        UserDto userDto = UserDto.builder().name("name").email("email").password("password").build();

        when(userBoardRepository.delete(any(),any())).thenReturn(1);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(boardDto1.toEntity()));
        when(userRepository.findOneById(any())).thenReturn(Optional.ofNullable(userDto.toEntity()));

        int result = userBoardService.cancelApply(userDto.getId(),boardDto1.getId());

        assertEquals(1,result);
    }


}
