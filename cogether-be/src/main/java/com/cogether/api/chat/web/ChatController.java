package com.cogether.api.chat.web;

import com.cogether.api.chat.domain.ChatRequest;
import com.cogether.api.chat.domain.ChatResponse;
import com.cogether.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<ChatResponse.GetChat> getChat(@PathVariable int chatId) {
        ChatResponse.GetChat response = chatService.getChat(chatId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/chat/list/{chatRoomId}", headers = "ACCESS_TOKEN")
    public ResponseEntity<ChatResponse.GetChats> getChats(@PathVariable int chatRoomId, @RequestHeader("ACCESS_TOKEN") String token) {
        ChatResponse.GetChats response = chatService.getChats(chatRoomId, token);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/chat/member")
    public ResponseEntity<ChatResponse.OnlyMemId> updateLastChat(@RequestBody ChatRequest.UpdateLastChat request) {
        ChatResponse.OnlyMemId response = chatService.updateLastChat(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/chat/room", headers = "ACCESS_TOKEN")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ChatResponse.OnlyRoomId> createChatRoom(@RequestBody ChatRequest.CreateChatRoom request, @RequestHeader("ACCESS_TOKEN") String token) {
        ChatResponse.OnlyRoomId response = chatService.createChatRoom(request, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/chat/room/list", headers = "ACCESS_TOKEN")
    public ResponseEntity<ChatResponse.GetChatRooms> getChatRooms(@RequestHeader("ACCESS_TOKEN") String token) {
        ChatResponse.GetChatRooms response = chatService.getChatRooms(token);
        return ResponseEntity.ok().body(response);
    }

}
