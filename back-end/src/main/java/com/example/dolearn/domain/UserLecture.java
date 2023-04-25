package com.example.dolearn.domain;

import com.example.dolearn.dto.UserLectureDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Entity(name="user_lecture")
@Table(name="member_lecture")
public class UserLecture {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="uid")
    private User user;

    @ManyToOne
    @JoinColumn(name="lid", nullable = false)
    private Lecture lecture;

    @Column(name = "evaluate_status")
    private int evaluateStatus;

    @Column(name="member_type", length = 10, nullable = false)
    private String memberType;

    public UserLectureDto toDto(){
        return UserLectureDto.builder()
                .id(id).lid(lecture.getId()).uid(user.getId()).lecture(lecture).user(user).evaluateStatus(evaluateStatus).memberType(memberType).build();
    }
}
