package com.example.dolearn.domain;

import com.example.dolearn.dto.UserBoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "member_board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "bid")
    private Long bid;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "bid", insertable = false, updatable = false)
    private Board board;

    @Column(name = "member_type")
    private String userType;

    public UserBoardDto toDto(){
        return UserBoardDto.builder()
                .id(id).uid(user.getId()).bid(board.getId()).board(board).user(user).userType(userType).build();
    }
}