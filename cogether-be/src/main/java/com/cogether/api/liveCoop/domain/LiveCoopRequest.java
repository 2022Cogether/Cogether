package com.cogether.api.liveCoop.domain;

import com.cogether.api.chat.domain.ChatRoom;
import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

public class LiveCoopRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateLiveCoop {
        private int userId;
        private int maxMemNum;
        private int duration;
        private String title;
        private String content;
        private boolean inProgress;

        public LiveCoop toEntity(User user, ChatRoom chatRoom) {
            return LiveCoop.builder()
                    .user(user)
                    .memNum(maxMemNum)
                    .nowMemNum(1)
                    .duration(duration)
                    .title(title)
                    .content(content)
                    .inProgress(inProgress)
                    .chatRoom(chatRoom)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StartLiveCoop {
        private int liveCoopId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateLiveCoopMember {
        private int userId;
        private int liveCoopId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateLiveCoopMember {
        private int liveCoopMemberId;
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SocketLiveCoop {
        private int liveCoopId;
        private int mode;
    }


}
