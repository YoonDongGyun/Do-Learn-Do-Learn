package com.example.dolearn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
public class FixedLectureDto {

    Long id;

    private Long bid;

    private Long uid;

    private String title;

    private String content;

    private String summary;

    private int maxCnt;

    private int students;

    private int instructors;

    private int isFixed;

    private String startTime;

    private String endTime;

    private String deadline;

    private String createdTime;

    public FixedLectureDto(Long id, Long bid, Long uid, String title, String content, String summary,
                           int maxCnt, long instructors, long students, int isFixed,
                           Date startTime, Date endTime, Date deadline, Date createdTime) {
        this.id = id;
        this.bid = bid;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.maxCnt = maxCnt;
        this.instructors = (int)instructors;
        this.students = (int)students;
        this.isFixed = isFixed;
        this.startTime = castToString(startTime);
        this.endTime = castToString(endTime);
        this.deadline = castToString(deadline);
        this.createdTime = castToString(createdTime);
    }

    public String castToString(Date time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
}