package com.cogether.api.chat.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

public class ChatRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateChatRoom {
        private boolean type;

        public ChatRoom toEntity() {
            return ChatRoom.builder()
                    .type(type)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateChat {

        private int chatRoomId;

        private int sendUserId;

        private String message;

        public Chat toEntity(ChatRoom chatRoom) {
            return Chat.builder()
                    .chatRoom(chatRoom)
                    .sendUserId(sendUserId)
                    .message(message)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateChatMember {

        private int userId;
        private int chatRoomId;

        public ChatMember toEntity(ChatRoom chatRoom, User user) {
            return ChatMember.builder()
                    .user(user)
                    .chatRoom(chatRoom)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateLastChat {
        private int userId;
        private int chatRoomId;
        private int chatId;
    }

}
