package com.example.dolearn.domain;

import com.example.dolearn.dto.LectureDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Entity(name="lecture")
public class Lecture {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_cnt", nullable = false)
    private int memberCnt;

    @Column(name="created_time")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="start_realtime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startRealTime;

    @Column(name="end_realtime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endRealTime;

    @JoinColumn(name="bid")
    @OneToOne
    private Board board;

    public LectureDto toMessageDto() {
        return null;
    }

    public LectureDto toDto(){
        return LectureDto.builder()
                .id(id).bid(board.getId()).memberCnt(memberCnt).createdTime(createdDate)
                .startRealTime(startRealTime).endRealTime(endRealTime).build();
    }
}
