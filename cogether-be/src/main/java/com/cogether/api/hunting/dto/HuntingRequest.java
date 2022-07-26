package com.cogether.api.hunting.dto;

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
        //TODO : 수정할 때 userId 백에서도 확인할지 논의 (안 한다면 안 받아도 됨)
        private int id;
        private int userId;
        private String title;
        private String content;
    }

}
