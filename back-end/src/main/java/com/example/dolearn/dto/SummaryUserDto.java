package com.example.dolearn.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryUserDto {

    private String name;

    private String email;

    private String info;

    private Integer point;

    private String youtube;

    private String instagram;

    private String facebook;

    private String blog;

    private String imgUrl;
}
