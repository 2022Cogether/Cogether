package com.cogether.api.hunting.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

public class HuntingRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateHunting {
        private int userId;
        private String title;
        private String content;

        public Hunting toEntity(User user) {
            return Hunting.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateHuntingScrap {
        private int userId;
        private int huntingId;

        public HuntingScrap toEntity(User user, Hunting hunting) {
            return HuntingScrap.builder()
                    .user(user)
                    .hunting(hunting)
                    .build();
        }
    }

}
