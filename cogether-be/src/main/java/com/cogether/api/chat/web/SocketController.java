package com.cogether.api.chat.web;

import com.cogether.api.chat.domain.ChatRequest;
import com.cogether.api.chat.domain.ChatResponse;
import com.cogether.api.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class SocketController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private final ChatService chatService;

    @MessageMapping(value = "/receive")
    public void message(ChatRequest.CreateChat request){

        ChatResponse.GetChat response = chatService.createChat(request);

        template.convertAndSend("/send/" + request.getChatRoomId(), response);
    }

}
