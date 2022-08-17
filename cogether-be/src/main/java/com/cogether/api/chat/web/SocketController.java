package com.cogether.api.chat.web;

import com.cogether.api.chat.domain.ChatRequest;
import com.cogether.api.chat.domain.ChatResponse;
import com.cogether.api.chat.service.ChatService;
import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.service.LiveCoopService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class SocketController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private final ChatService chatService;

    private final LiveCoopService liveCoopService;

    @MessageMapping(value = "/receive")
    public void chat(ChatRequest.CreateChat request) {

        ChatResponse.GetChat response = chatService.createChat(request);

        template.convertAndSend("/send/" + request.getChatRoomId(), response);
    }

    @MessageMapping(value = "/receive/coop")
    public void getLiveCoop(LiveCoopRequest.SocketLiveCoop request) {
        LiveCoopResponse.SocketLiveCoop response = liveCoopService.socketLiveCoop(request);
        template.convertAndSend("/send/coop/" + request.getLiveCoopId(), response);
    }

}
