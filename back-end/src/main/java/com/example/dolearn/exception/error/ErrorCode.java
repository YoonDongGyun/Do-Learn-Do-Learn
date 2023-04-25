package com.example.dolearn.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common
    INVALID_INPUT(HttpStatus.METHOD_NOT_ALLOWED, "405", "기입되지 않은 정보가 있습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 에러"),

    // user
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "409", "이미 존재하는 이메일입니다."),
    NO_USER(HttpStatus.BAD_REQUEST, "400", "없는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "400", "비밀번호가 옳지 않습니다."),
    NO_LOGIN(HttpStatus.UNAUTHORIZED, "401", "로그인이 필요합니다"),

    // jwt
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "403", "유효하지 않은 토큰입니다"),

    NO_MESSSAGE(HttpStatus.NOT_FOUND,"404","없는 메세지 입니다."),

    NO_BOARD(HttpStatus.NOT_FOUND,"404","게시물이 없습니다."),

    NO_LECTURE(HttpStatus.NOT_FOUND,"404","유효하지 않은 강의입니다"),

    NO_STUDENTS(HttpStatus.NOT_FOUND,"404","신청한 학생이 없습니다."),

    NO_INSTRUCTORS(HttpStatus.NOT_FOUND,"404","신청한 강사가 없습니다."),

    NO_APPLICANT(HttpStatus.NOT_FOUND,"404","신청 내역이 없습니다."),

    FIXED_LECTURE(HttpStatus.CONFLICT,"409","이미 확정된 강의입니다."),

    EXEED_STUDENTS(HttpStatus.BAD_REQUEST, "400","신청 학생 수를 초과하였습니다");

    private HttpStatus httpStatus;
    private String code;
    private String message;
}
