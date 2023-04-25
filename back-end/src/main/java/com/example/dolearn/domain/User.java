package com.example.dolearn.domain;

import com.example.dolearn.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="user")
@Table(name="member")
public class User {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 3000)
    private String info;

    @Column
    private Integer point;

    @Column(length = 50)
    private String youtube;

    @Column(length = 50)
    private String instagram;

    @Column(length = 200)
    private String facebook;

    @Column(length = 200)
    private String blog;

    @Column(name="img_path", length = 200)
    private String imgPath;

    @Column(name="img_url", length = 200)
    private String imgUrl;

    @Column(name="refresh_token", length = 500)
    private String refreshToken;

    @Column(name="join_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserBoard> userBoardList = new ArrayList<>();

    @PrePersist
    public void setDefaultValue(){
        this.info = (this.info == null) ? "" : this.info;
        this.point = (this.point == null) ? 100 : this.point;
        this.youtube = (this.youtube == null) ? "" : this.youtube;
        this.instagram = (this.instagram == null) ? "" : this.instagram;
        this.facebook = (this.facebook == null) ? "" : this.facebook;
        this.blog = (this.blog == null) ? "" : this.blog;
        this.imgPath = (this.imgPath == null) ? "" : this.imgPath;
        this.imgUrl = (this.imgUrl == null) ? "" : this.imgUrl;
    }

    public User update(String name,String email) {
        this.name = name;
        this.email = email;
        return this;
    }
    //소셜로그인시만 사용
    public void updatePassword(String password) {
        this.password = password;
    }

    //소셜로그인 성공후 리프레쉬토큰 저장
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserDto toDto() {
        return UserDto.builder()
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

