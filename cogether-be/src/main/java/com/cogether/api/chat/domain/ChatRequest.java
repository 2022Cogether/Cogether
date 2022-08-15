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
        private int chatMemberUserId;
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
