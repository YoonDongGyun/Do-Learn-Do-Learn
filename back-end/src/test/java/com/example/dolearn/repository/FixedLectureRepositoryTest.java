package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.*;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.FixedLectureDto;
import com.example.dolearn.dto.UserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class FixedLectureRepositoryTest {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserBoardRepository userBoardRepository;

    @Autowired
    UserLectureRepository userLectureRepository;


    @Test
    @DisplayName("사용자가 신청한 확정 강의 내역 조회 성공")
    public void findFixedLecture() throws Exception{
        FixedLectureRepository fixedLectureRepository = new FixedLectureRepository(jpaQueryFactory);

        User user1 = UserDto.builder().id(1L).name("김싸피").email("ssafy@naver.com").password("abcd!1234").build().toEntity();
        User student = userRepository.save(user1);
        User user2 = UserDto.builder().id(2L).name("선생님").email("ssafy1@naver.com").password("abcd!1234").build().toEntity();
        User instructor = userRepository.save(user2);

        Board _board = BoardDto.builder()
                .uid(student.getId()).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(1).maxCnt(5).summary("summary").title("title").build().toEntity();
        Board savedBoard = boardRepository.save(_board);

        UserBoard stuBoard = UserBoard.builder().bid(savedBoard.getId()).uid(student.getId()).board(savedBoard).user(student).userType("학생").build();
        UserBoard instBoard = UserBoard.builder().bid(savedBoard.getId()).uid(instructor.getId()).board(savedBoard).user(instructor).userType("강사").build();
        userBoardRepository.save(stuBoard);
        userBoardRepository.save(instBoard);

        Date now = new Date();
        Lecture _lecture = Lecture.builder().id(1L).board(savedBoard).createdDate(now).startRealTime(now).endRealTime(now).memberCnt(2).build();
        Lecture savedLecture = lectureRepository.save(_lecture);

        UserLecture stdLecture = UserLecture.builder().user(student).lecture(savedLecture).evaluateStatus(0).memberType("학생").build();
        UserLecture instLecture = UserLecture.builder().user(instructor).lecture(savedLecture).evaluateStatus(0).memberType("강사").build();
        userLectureRepository.save(stdLecture);
        userLectureRepository.save(instLecture);

        List<FixedLectureDto> result = fixedLectureRepository.findFixedLecture(student.getId());
        assertThat(result.size()).isEqualTo(1);
    }
}
