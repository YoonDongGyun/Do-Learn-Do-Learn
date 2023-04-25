package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("lecture repository 저장 테스트")
    @Test
    public void LectureRepositorySaveTest() {
        Lecture lecture = Lecture
                .builder()
                .memberCnt(0).build();

        Lecture result = lectureRepository.save(lecture);

        assertThat(result.getMemberCnt()).isEqualTo(lecture.getMemberCnt());
    }

    @DisplayName("lecture repository findByBoardId 테스트")
    @Test
    public void LectureRepositoryFindByBoardIdTest() {

        Board board = Board
                .builder()
                .title("test title")
                .content("test board")
                .build();

        Lecture lecture = Lecture
                .builder()
                .id(1L)
                .board(board).build();

        Board boardResult = boardRepository.save(board);
        lectureRepository.save(lecture);

        Lecture result = lectureRepository.findByBoardId(boardResult.getId());

        assertThat(result.getBoard().getId()).isEqualTo(boardResult.getId());
    }
}
