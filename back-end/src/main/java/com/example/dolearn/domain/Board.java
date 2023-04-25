package com.example.dolearn.domain;

import com.example.dolearn.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate //Update 시에 변경된 필드만 대응
@Slf4j
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Long uid; //user table의 id (foreign key)

    @Column(name = "tid")
    private Long tid; //thumbnail 이미지 id

    @Column(name = "title")
    private String title;

    @Column(name = "max_cnt")
    private int maxCnt;

    @Column(name = "content")
    private String content;

    @Column(name = "summary")
    private String summary;

    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "deadline")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @Column(name = "is_fixed")
    private int isFixed;
    @Column(name = "created_time")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<UserBoard> userBoardList = new ArrayList<>();

    public BoardDto toDto(){
        return BoardDto.builder()
                .id(id).uid(uid).tid(tid).title(title).maxCnt(maxCnt).content(content).summary(summary).startTime(stringConverter(startTime))
                .endTime(stringConverter(endTime)).deadline(stringConverter(deadline)).isFixed(isFixed).createdTime(createdTime).build();
    }

    public String stringConverter(Date input){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = formatter.format(input);

        log.info("date: {}",date);

        return date;
    }
}