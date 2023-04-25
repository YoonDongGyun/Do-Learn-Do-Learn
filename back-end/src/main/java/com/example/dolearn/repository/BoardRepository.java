package com.example.dolearn.repository;

import com.example.dolearn.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository <Board, Long>, QBoardRepository{
    @Modifying
    @Query(value = "delete from board where id=:bid")
    int deleteBoard(@Param("bid") Long board_id);

    List<Board> findByUid(Long uid);

    List<Board> findByTitleContaining(String keyword);

    List<Board> findByContentContaining(String keyword);

    List<Board> findBySummaryContaining(String keyword);

}
