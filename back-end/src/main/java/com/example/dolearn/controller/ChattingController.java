package com.example.dolearn.controller;

import com.example.dolearn.dto.ChatMessageDto;
import com.example.dolearn.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChattingController {
    private final SimpMessagingTemplate template;

    // 클라이언트에서 /pub/channel 로 메시지를 발생
    @MessageMapping("/normal/{id}")
    public void message(@DestinationVariable Long id, Message<ChatMessageDto> message, @Header("Authentication") String accessToken){
        // /sub/channel/채널아이디에 구독중인 클라이언트에게 메시지 전송

        ChatMessageDto chatMessageDto = message.getPayload();
        log.info("access token : {}",accessToken );
        log.info("chat room Id :{}", chatMessageDto.getRoomId());
        template.convertAndSend("/sub/"+ id, chatMessageDto);
    }

//    @MessageMapping("/pub/{id}")
//    public void message(@PathVariable("id") Long id, ChatMessageDto messageDto){
//        // /sub/channel/채널아이디에 구독중인 클라이언트에게 메시지 전송
//        log.info("?accessToken");
//        template.convertAndSend("/sub/"+id, messageDto);
//    }
}
