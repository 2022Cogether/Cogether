package com.cogether.api.liveCoop.domain;

import lombok.*;

import java.time.LocalTime;

public class LiveCoopResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private long id;

        public static LiveCoopResponse.OnlyId build(LiveCoop liveCoop) {
            return LiveCoopResponse.OnlyId.builder()
                    .id(liveCoop.getId())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveCoop {
        private int id;
        private int userId;
        private String userNickname;
        private String userImgUrl;
        private int memNum;
        private int nowMemNum;
        private LocalTime duration;
        private String title;
        private String content;
        private boolean inProgress;

        public static GetLiveCoop build(LiveCoop liveCoop) {
            return GetLiveCoop.builder()
                    .id(liveCoop.getId())
                    .userId(liveCoop.getUser().getId())
                    .userNickname(liveCoop.getUser().getNickname())
                    .userImgUrl(liveCoop.getUser().getImgUrl())
                    .memNum(liveCoop.getMemNum())
                    .nowMemNum(liveCoop.getNowMemNum())
                    .duration(liveCoop.getDuration())
                    .title(liveCoop.getTitle())
                    .content(liveCoop.getContent())
                    .inProgress(liveCoop.isInProgress())
                    .build();
        }
    }

}
