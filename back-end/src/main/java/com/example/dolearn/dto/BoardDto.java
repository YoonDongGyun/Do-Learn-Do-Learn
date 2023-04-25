package com.example.dolearn.dto;

import com.example.dolearn.domain.Board;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Slf4j
public class BoardDto {
    private Long id;

    private Long uid; //user table의 id (foreign key)

    private Long tid;//thumbnail table의 id (foreign key)

    private String title;

    private int maxCnt;

    private String content;

    private String summary;

    private String startTime;

    private String endTime;

    private String deadline;

    private int isFixed;

    private Date createdTime;

    private int instructors;

    private int students;

    public void setCounts(int instructors, int students){
        this.instructors= instructors;
        this.students= students;

        log.info("강사 수: {} 학생 수: {}",instructors,students);
    }

    public void setTimes(){
        String[] temp = startTime.split(" ");
        int hour = Integer.parseInt(temp[1])+Integer.parseInt(endTime);

        startTime = this.startTime.concat(":00:00");
        endTime = temp[0].concat(String.format(" %d:00:00",hour));

        if(temp[0].equals(deadline)){
            String[] start = startTime.split(" ");
            deadline= deadline.concat(" "+ start[1]);
        }
        else deadline = deadline.concat(" 23:59:59");

        log.info("startTime: {} endTime:{}",startTime, endTime);
    }

    public int checkDeadline() throws Exception{
        Date today = new Date();
        Date deadline = dateConverter(this.deadline);
        int result = today.compareTo(deadline);

        return result;
    }

    public Board toEntity() throws ParseException {
        return Board.builder()
                .id(id)
                .uid(uid)
                .tid(tid)
                .title(title)
                .maxCnt(maxCnt)
                .content(content)
                .summary(summary)
                .startTime(dateConverter(startTime))
                .endTime(dateConverter(endTime))
                .deadline(dateConverter(deadline))
                .isFixed(isFixed)
                .createdTime(createdTime).build();
    }

    public void setFixed(int isFixed){
        this.isFixed = isFixed;
    }

    public Date dateConverter(String input) throws ParseException {
        SimpleDateFormat foramatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = foramatter.parse(input);

        return date;
    }
}
