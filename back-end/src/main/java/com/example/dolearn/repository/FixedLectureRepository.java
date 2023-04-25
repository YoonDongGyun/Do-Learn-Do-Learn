package com.example.dolearn.repository;

import com.example.dolearn.dto.FixedLectureDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.dolearn.domain.QBoard.board;
import static com.example.dolearn.domain.QLecture.lecture;
import static com.example.dolearn.domain.QUserBoard.userBoard;
import static com.example.dolearn.domain.QUserLecture.userLecture;
import static com.querydsl.core.types.ExpressionUtils.count;

@Repository
public class FixedLectureRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FixedLectureRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<FixedLectureDto> findFixedLecture(Long uid) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        FixedLectureDto.class, lecture.id, board.id, board.uid, board.title, board.content, board.summary
                        , board.maxCnt,
                        ExpressionUtils.as(
                                JPAExpressions.select(count(userBoard.id))
                                        .from(userBoard)
                                        .where(userBoard.bid.eq(board.id), userBoard.userType.eq("강사")),
                                "instructors"),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(userBoard.id))
                                        .from(userBoard)
                                        .where(userBoard.bid.eq(board.id), userBoard.userType.eq("학생")),
                                "students"),
                        board.isFixed, board.startTime, board.endTime, board.deadline, board.createdTime))
                .from(userLecture)
                .innerJoin(userLecture.lecture, lecture)
                .innerJoin(lecture.board, board)
                .where(userLecture.user.id.eq(uid))
                .fetch();
    }
}
