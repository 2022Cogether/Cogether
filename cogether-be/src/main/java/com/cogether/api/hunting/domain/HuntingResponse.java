package com.cogether.api.hunting.domain;

import com.cogether.api.user.dto.UserSkillResponse;
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
        private int huntingId;
        private int userId;
        private String userNickname;
        private String userImgUrl;
        private String title;
        private String content;
        private boolean scrap;
        private int scrapId;
        private List<UserSkillResponse> userSkillList;

        public static HuntingResponse.GetHunting build(Hunting hunting, boolean scrap, int scrapId, List<UserSkillResponse> userSkillList) {
            return GetHunting.builder()
                    .huntingId(hunting.getId())
                    .userId(hunting.getUser().getId())
                    .userNickname(hunting.getUser().getNickname())
                    .userImgUrl(hunting.getUser().getImgUrl())
                    .title(hunting.getTitle())
                    .content(hunting.getContent())
                    .scrap(scrap)
                    .scrapId(scrapId)
                    .userSkillList(userSkillList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetHuntings {

        private List<GetHunting> huntings;

        public static HuntingResponse.GetHuntings build(List<GetHunting> huntings) {
            return GetHuntings.builder()
                    .huntings(huntings)
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
