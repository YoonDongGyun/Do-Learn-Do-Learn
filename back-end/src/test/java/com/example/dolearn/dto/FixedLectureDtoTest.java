package com.example.dolearn.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class FixedLectureDtoTest {

    @Test
    void success(){
        Date now = new Date();
        String strNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
        FixedLectureDto fixedLectureDto = new FixedLectureDto(1L, 1L, 1L, "board title", "board content", "board summary",
            5, 1, 5, 1, now, now, now, now);

        assertThat(fixedLectureDto.getId()).isEqualTo(1L);
        assertThat(fixedLectureDto.getBid()).isEqualTo(1L);
        assertThat(fixedLectureDto.getUid()).isEqualTo(1L);
        assertThat(fixedLectureDto.getTitle()).isEqualTo("board title");
        assertThat(fixedLectureDto.getContent()).isEqualTo("board content");
        assertThat(fixedLectureDto.getSummary()).isEqualTo("board summary");
        assertThat(fixedLectureDto.getMaxCnt()).isEqualTo(5);
        assertThat(fixedLectureDto.getInstructors()).isEqualTo(1);
        assertThat(fixedLectureDto.getStudents()).isEqualTo(5);
        assertThat(fixedLectureDto.getIsFixed()).isEqualTo(1);
        assertThat(fixedLectureDto.getCreatedTime()).isEqualTo(strNow);
        assertThat(fixedLectureDto.getStartTime()).isEqualTo(strNow);
        assertThat(fixedLectureDto.getEndTime()).isEqualTo(strNow);
        assertThat(fixedLectureDto.getDeadline()).isEqualTo(strNow);
    }
}
