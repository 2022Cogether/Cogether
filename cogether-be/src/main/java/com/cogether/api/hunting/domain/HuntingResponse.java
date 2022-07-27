package com.cogether.api.hunting.domain;

import lombok.*;

public class HuntingResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private long id;

        public static HuntingResponse.OnlyId build(Hunting hunting) {
            return HuntingResponse.OnlyId.builder()
                    .id(hunting.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetHunting {
        // TODO: 게시물유저 skill들, 로그인유저 스크랩여부 추가
        private int id;
        private int userId;
        private String userNickname;
        private String userImgUrl;
        private String title;
        private String content;

        public static HuntingResponse.GetHunting build(Hunting hunting) {
            return GetHunting.builder()
                    .id(hunting.getId())
                    .userId(hunting.getUser().getId())
                    .userNickname(hunting.getUser().getNickname())
                    .userImgUrl(hunting.getUser().getImgUrl())
                    .title(hunting.getTitle())
                    .content(hunting.getContent())
                    .build();
        }
    }
}
