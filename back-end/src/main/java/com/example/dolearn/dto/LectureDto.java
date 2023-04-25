package com.example.dolearn.dto;

import com.example.dolearn.domain.Lecture;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.ParseException;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LectureDto {

    private Long id;
    private Long bid; //thumbnail table의 id (foreign key)
    private int memberCnt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endRealTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startRealTime;

    public Lecture toEntity() throws ParseException {
        return Lecture.builder()
                .createdDate(createdTime)
                .memberCnt(memberCnt)
                .endRealTime(endRealTime)
                .startRealTime(startRealTime)
                .build();
    }
}
