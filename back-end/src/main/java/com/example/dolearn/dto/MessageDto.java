package com.example.dolearn.dto;

import com.example.dolearn.domain.Message;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private Long rid;
    private Long bid;
    private String title; //강의 제목
    private String content;
    private String type;
    private int isChecked;
    private Date start_time; //강의 시작시간
    private Date end_time;
    private Date createdTime;
    private Date checkTime; //메세지 확인 시간

    public Message toEntity() {

        return Message.builder()
                .content(this.content)
                .checkTime(this.checkTime)
                .isChecked(this.isChecked)
                .build();
    }
}
