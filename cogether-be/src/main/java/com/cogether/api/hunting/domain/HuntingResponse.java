package com.cogether.api.hunting.domain;

import com.cogether.api.user.domain.UserSkill;
import lombok.*;

import java.util.List;

public class HuntingResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyHuntingId {
        private int huntingId;

        public static HuntingResponse.OnlyHuntingId build(Hunting hunting) {
            return OnlyHuntingId.builder()
                    .huntingId(hunting.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetHunting {
        // TODO: 게시물유저 skill들, 로그인유저 스크랩여부 추가
        private int huntingId;
        private int userId;
        private String userNickname;
        private String userImgUrl;
        private String title;
        private String content;
        private boolean scrap;
        private int scrapId;
        private List<UserSkill> userSkillList;

        public static HuntingResponse.GetHunting build(Hunting hunting, boolean scrap, int scrapId) {
            return GetHunting.builder()
                    .huntingId(hunting.getId())
                    .userId(hunting.getUser().getId())
                    .userNickname(hunting.getUser().getNickname())
                    .userImgUrl(hunting.getUser().getImgUrl())
                    .title(hunting.getTitle())
                    .content(hunting.getContent())
                    .scrap(scrap)
                    .scrapId(scrapId)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyHuntingScrapId {
        private int huntingScrapId;

        public static HuntingResponse.OnlyHuntingScrapId build(HuntingScrap huntingScrap) {
            return OnlyHuntingScrapId.builder()
                    .huntingScrapId(huntingScrap.getId())
                    .build();
        }
    }
}
