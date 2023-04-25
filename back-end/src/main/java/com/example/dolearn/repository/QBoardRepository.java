package com.example.dolearn.repository;

import com.example.dolearn.domain.Board;
import com.querydsl.core.Tuple;

import java.util.List;

public interface QBoardRepository {

    List<Board> findRequestLecture(Long uid);

    List<Board> findRequestLectureByHost(Long uid);

    List<Board> findRequestLectureByInst(Long uid);

    List<Board> findRequestLectureByStud(Long uid);
}
