package com.example.dolearn.dto;

import com.example.dolearn.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String name;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{9,16}", message = "비밀번호는 9~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String info;

    private Integer point;

    private String youtube;

    private String instagram;

    private String facebook;

    private String blog;

    private String imgPath;

    private String imgUrl;

    private String refreshToken;

    private String accessToken;

    Date joinDate;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .info(info)
                .point(point)
                .youtube(youtube)
                .instagram(instagram)
                .facebook(facebook)
                .blog(blog)
                .imgPath(imgPath)
                .imgUrl(imgUrl)
                .refreshToken(refreshToken)
                .joinDate(joinDate)
                .build();
    }
}
