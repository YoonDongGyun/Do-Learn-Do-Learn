package com.example.dolearn.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessageDto {

    private String sender;

    private String roomId;

    private String content;
}
