package com.example.dolearn.repository;

import com.example.dolearn.domain.Lecture;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.dolearn.domain.QBoard.board;
import static com.example.dolearn.domain.QLecture.lecture;

public class LectureBoardRepositoryImpl implements LectureBoardRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public LectureBoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Lecture findByBoardId(Long boardId) {
        return jpaQueryFactory.selectFrom(lecture)
                            .innerJoin(lecture.board, board)
                .where(board.id.eq(boardId)).fetchOne();

    }
}
