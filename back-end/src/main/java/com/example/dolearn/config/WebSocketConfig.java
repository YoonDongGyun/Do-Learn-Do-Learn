package com.example.dolearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/sub");    // 클라이인트 사용자 구독 경로(/sub/채널id)
        registry.setApplicationDestinationPrefixes("/pub");     // 메시지 발송 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws")    // 웹소켓 엔드포인트 정의
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}


