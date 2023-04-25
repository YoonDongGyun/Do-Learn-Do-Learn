package com.example.dolearn.repository;

import com.example.dolearn.domain.Message;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.dolearn.domain.QMessage.message;
import static com.example.dolearn.domain.QUser.user;

public class UserMessageRepositoryImpl implements UserMessageRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public UserMessageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //userId로 메세지 리스트 가져오기
    @Override
    public List<Message> findMessageByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(message)
                .innerJoin(message.user,user)
                .where(user.id.eq(userId))
                .orderBy(message.createdTime.desc())
                .fetch();

    }
}
