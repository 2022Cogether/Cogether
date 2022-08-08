package com.cogether.api.liveCoop.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalTime;

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

        public LiveCoop toEntity(User user) {
            return LiveCoop.builder()
                    .user(user)
                    .memNum(maxMemNum)
                    .nowMemNum(1)
                    .duration(duration)
                    .title(title)
                    .content(content)
                    .inProgress(inProgress)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateLiveCoopMember {
        private int userId;
        private  int liveCoopId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateLiveCoopMember {
        private  int liveCoopMemberId;
        private String code;
    }

}
