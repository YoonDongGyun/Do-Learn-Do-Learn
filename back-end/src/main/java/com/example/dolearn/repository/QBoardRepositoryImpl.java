package com.example.dolearn.repository;

import com.example.dolearn.domain.Board;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Date;
import java.util.List;

import static com.example.dolearn.domain.QBoard.board;
import static com.example.dolearn.domain.QLecture.lecture;
import static com.example.dolearn.domain.QUserBoard.userBoard;

public class QBoardRepositoryImpl extends QuerydslRepositorySupport implements QBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QBoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Board.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findRequestLecture(Long uid) {
        return jpaQueryFactory
                .selectDistinct(board)
                .from(board)
                .leftJoin(board.userBoardList, userBoard)
                .where(board.isFixed.eq(0), (board.uid.eq(uid).or(userBoard.uid.eq(uid))), board.deadline.gt(new Date()))
                .fetch();
    }

    @Override
    public List<Board> findRequestLectureByHost(Long uid) {
        return jpaQueryFactory
                .selectFrom(board)
                .where(board.isFixed.eq(0), board.uid.eq(uid), board.deadline.gt(new Date()))
                .fetch();
    }

    @Override
    public List<Board> findRequestLectureByInst(Long uid) {
        return jpaQueryFactory
                .selectDistinct(board)
                .from(board)
                .leftJoin(board.userBoardList, userBoard)
                .where(board.isFixed.eq(0), userBoard.userType.eq("강사"), userBoard.uid.eq(uid), board.deadline.gt(new Date()))
                .fetch();
    }

    @Override
    public List<Board> findRequestLectureByStud(Long uid) {
        return jpaQueryFactory
                .selectDistinct(board)
                .from(board)
                .leftJoin(board.userBoardList, userBoard)
                .where(board.isFixed.eq(0), (board.uid.eq(uid).or((userBoard.uid.eq(uid).and(userBoard.userType.eq("학생"))))), board.deadline.gt(new Date()))
                .fetch();
    }
}
