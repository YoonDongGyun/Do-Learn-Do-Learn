package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserBoard;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")

public class UserBoardRepositoryTest {
    @Autowired
    UserBoardRepository userBoardRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    private final BoardDto boardDto1 = BoardDto.builder()
            .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    private final BoardDto boardDto2 = BoardDto.builder()
            .id(2L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    private final UserDto userDto = UserDto.builder().id(1L).name("name").email("email").password("password").build();

    private final UserDto userDto2 = UserDto.builder().id(2L).name("name").email("email").password("password").build();

    @DisplayName("강의 신청 테스트")
    @Test
    public void UserBoardRepositorySaveTest() throws Exception{
        Board boardSaved = boardRepository.save(boardDto2.toEntity());

        User userSaved = userRepository.save(userDto2.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .bid(boardSaved.getId()).uid(userSaved.getId()).board(boardDto2.toEntity()).user(userDto.toEntity()).userType("강사").build();

        UserBoard result = userBoardRepository.save(userBoard);

        assertEquals(userBoard.getId(),result.getId());

    }

    @DisplayName("강사 목록 조회 테스트")
    @Test
    public void UserBoardRepositoryInstructorsTest() throws Exception{
        Board boardSaved = boardRepository.save(boardDto1.toEntity());

        User userSaved = userRepository.save(userDto.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .bid(boardSaved.getId()).uid(userSaved.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("강사").build();

        List<UserBoard> userBoardList = new ArrayList<>();

        userBoardList.add(userBoard);

        UserBoard result = userBoardRepository.save(userBoard);

        List<UserBoard> resultList = userBoardRepository.findInstructors(userBoard.getBid());

        assertEquals(userBoardList.size(),resultList.size());
    }

    @DisplayName("학생 목록 가져오기 테스트")
    @Test
    public void UserBoardRepositoryStudentsTest() throws Exception{

        Board boardSaved = boardRepository.save(boardDto1.toEntity());

        User userSaved = userRepository.save(userDto.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .bid(boardSaved.getId()).uid(userSaved.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        List<UserBoard> userBoardList = new ArrayList<>();

        userBoardList.add(userBoard);

        UserBoard saved = userBoardRepository.save(userBoard);

        List<UserBoard> result = userBoardRepository.findStudents(userBoard.getBid());

        assertEquals(userBoardList.size(),result.size());
    }

    @DisplayName("삭제 테스트")
    @Test
    public void UserBoardRepositoryDeleteTest() throws Exception{
        Board boardSaved = boardRepository.save(boardDto1.toEntity());

        User userSaved = userRepository.save(userDto.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .bid(boardSaved.getId()).uid(userSaved.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        UserBoard saved = userBoardRepository.save(userBoard);

        int result = userBoardRepository.delete(saved.getUid(),saved.getBid());

        assertEquals(1,result);
    }

    @DisplayName("bid로 삭제 테스트")
    @Test
    public void UserBoardRepositoryDeleteByBidTest() throws Exception{
        Board boardSaved = boardRepository.save(boardDto1.toEntity());

        User userSaved = userRepository.save(userDto.toEntity());

        UserBoard userBoard= UserBoard.builder()
                .bid(boardSaved.getId()).uid(userSaved.getId()).board(boardDto1.toEntity()).user(userDto.toEntity()).userType("학생").build();

        UserBoard saved = userBoardRepository.save(userBoard);

        int result = userBoardRepository.deleteByBid(saved.getBid());

        assertEquals(1,result);
    }
}
