package com.example.dolearn.dto;

import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserBoard;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserBoardDto {

    private Long id;

    private Long bid;

    private Long uid;

    private User user;

    private Board board;

    private String userType;

    public UserBoard toEntity(){
        return UserBoard.builder()
                .id(id)
                .bid(bid)
                .uid(uid)
                .user(user)
                .board(board)
                .userType(userType)
                .build();
    }
}
