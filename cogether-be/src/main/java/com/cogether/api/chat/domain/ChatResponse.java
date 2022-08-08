package com.cogether.api.chat.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChatResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyChatId {
        private int id;

        public static ChatResponse.OnlyChatId build(Chat chat) {
            return ChatResponse.OnlyChatId.builder()
                    .id(chat.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyMemId {
        private int id;

        public static ChatResponse.OnlyMemId build(ChatMember chatMember) {
            return ChatResponse.OnlyMemId.builder()
                    .id(chatMember.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyRoomId {
        private int id;

        public static ChatResponse.OnlyRoomId build(ChatRoom chatRoom) {
            return ChatResponse.OnlyRoomId.builder()
                    .id(chatRoom.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetChat {
        private int chatId;
        private int sendUserId;
        private String sendUserImg;
        private String sendUserName;
        private String message;
        private LocalDateTime createdAt;

        public static ChatResponse.GetChat build(Chat chat, User user) {
            return GetChat.builder()
                    .chatId(chat.getId())
                    .sendUserId(chat.getSendUserId())
                    .sendUserImg(user.getImgUrl())
                    .sendUserName(user.getNickname())
                    .message(chat.getMessage())
                    .createdAt(chat.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetChats {
        private List<ChatResponse.GetChat> chats;

        public static ChatResponse.GetChats build(List<ChatResponse.GetChat> chats) {
            return ChatResponse.GetChats.builder()
                    .chats(chats)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetChatMember {
        private int chatMemberId;
        private String chatMemberImg;
        private String chatMemberName;

        public static ChatResponse.GetChatMember build(ChatMember chatMember) {
            return GetChatMember.builder()
                    .chatMemberId(chatMember.getId())
                    .chatMemberImg(chatMember.getUser().getImgUrl())
                    .chatMemberName(chatMember.getUser().getNickname())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetChatRoom {
        private int id;
        private boolean type;
        private List<ChatResponse.GetChatMember> chatMembers;

        public static ChatResponse.GetChatRoom build(ChatRoom chatRoom, List<ChatMember> chatMembers) {
            return GetChatRoom.builder()
                    .id(chatRoom.getId())
                    .type(chatRoom.isType())
                    .chatMembers(chatMembers.stream().map(ChatResponse.GetChatMember::build).collect(Collectors.toList()))
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetChatRooms {
        private List<ChatResponse.GetChatRoom> chatRooms;

        public static ChatResponse.GetChatRooms build(List<ChatResponse.GetChatRoom> chatRooms) {
            return ChatResponse.GetChatRooms.builder()
                    .chatRooms(chatRooms)
                    .build();
        }
    }
}
