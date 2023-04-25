package com.example.dolearn.service;

import com.example.dolearn.dto.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {
    public ChatMessageDto sendMessage(ChatMessageDto message, String accessToken) throws Exception {

       //jwt로 유저 검증

        // 채팅 메세지 반환
//        return ChatMessageDto
//                .builder()
//                .content(message.getContent())
//                .sender(writer.getUserName()).build();
        return null;
    }
}
