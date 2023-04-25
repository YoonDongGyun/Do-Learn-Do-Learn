package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.Board;
import com.example.dolearn.dto.BoardDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    private final BoardDto boardDto1 = BoardDto.builder()
            .uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    private final BoardDto boardDto2 = BoardDto.builder()
            .uid(2L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
            .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
            .isFixed(0).maxCnt(5).summary("summary").title("title").build();

    @DisplayName("상세 정보보기 테스트")
    @Test
    public void boardRepositoryFindByIdTest() throws Exception{
        Board savedBoard = boardRepository.save(boardDto2.toEntity());

        Optional<Board> result = boardRepository.findById(savedBoard.getId());

        Board board=result.get();

        assertThat(board.getContent()).isEqualTo(boardDto2.getContent());
    }

    @DisplayName("저장 테스트")
    @Test
    public void boardRepositorySaveTest() throws Exception{
        Board result = boardRepository.save(boardDto1.toEntity());

        assertEquals(boardDto1.getContent(),result.getContent());
    }

    @DisplayName("목록 불러오기 테스트")
    @Test
    public void boardRepositoryFindAllTest() throws Exception{
        List<Board> boardList = new ArrayList<>();
        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        boardRepository.save(boardDto1.toEntity());
        boardRepository.save(boardDto2.toEntity());

        List<Board> result = boardRepository.findAll();

        assertEquals(result.size(),boardList.size());
    }



    @DisplayName("삭제 테스트")
    @Test
    public void boardRepositoryDeleteTest() throws Exception{
        Board savedBoard = boardRepository.save(boardDto1.toEntity());

        int result = boardRepository.deleteBoard(savedBoard.getId());

        assertEquals(result, 1);
    }

    @DisplayName("제목으로 검색 테스트")
    @Test
    public void boardRepositoryFindByTitleTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardRepository.save(boardDto1.toEntity());
        boardRepository.save(boardDto2.toEntity());

        boardList.add(boardDto1.toEntity());
        boardList.add(boardDto2.toEntity());

        List<Board> result = boardRepository.findByTitleContaining("title");

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("내용으로 검색 테스트")
    @Test
    public void boardRepositoryFindByContentTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardRepository.save(boardDto1.toEntity());

        boardList.add(boardDto1.toEntity());

        List<Board> result = boardRepository.findByContentContaining("content");

        assertEquals(boardList.size(),result.size());
    }

    @DisplayName("요약으로 검색 테스트")
    @Test
    public void boardRepositoryFindBySummaryTest() throws Exception{
        List<Board> boardList = new ArrayList<>();

        boardRepository.save(boardDto1.toEntity());

        boardList.add(boardDto1.toEntity());

        List<Board> result = boardRepository.findBySummaryContaining("summary");

        assertEquals(boardList.size(),result.size());
    }
}

