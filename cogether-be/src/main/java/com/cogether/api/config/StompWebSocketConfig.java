package com.cogether.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    // 클라이언트가 메시지를 구독할 endpoint 정의
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/send");
    }

    @Override
    // connection을 맺을때 CORS 허용
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                // 스프링 5.3, 스프링부트 2.4 버전 부터 allowCredentials이 true인 경우 setAllowedOrigins 메서드에서 와일드 카드 `'*'`을 사용할 수 없음
                // .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    //    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //registry.setApplicationDestinationPrefixes("/pub");
//        registry.enableSimpleBroker("/sub");
//    }
}