package com.cogether.api.hunting.domain;

import com.cogether.api.user.dto.User;
import lombok.*;

public class HuntingRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create {
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
    public static class Update {
        private int id;
        private String title;
        private String content;
    }

}
