package com.cogether.api.liveCoop.domain;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class LiveCoopResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private int id;

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
        private String userName;
        private String userImgUrl;
        private int maxMemNum;
        private int nowMemNum;
        private int duration;
        private String title;
        private String content;
        private boolean inProgress;

        public static GetLiveCoop build(LiveCoop liveCoop) {
            return GetLiveCoop.builder()
                    .id(liveCoop.getId())
                    .userId(liveCoop.getUser().getId())
                    .userName(liveCoop.getUser().getNickname())
                    .userImgUrl(liveCoop.getUser().getImgUrl())
                    .maxMemNum(liveCoop.getMemNum())
                    .nowMemNum(liveCoop.getNowMemNum())
                    .duration(liveCoop.getDuration())
                    .title(liveCoop.getTitle())
                    .content(liveCoop.getContent())
                    .inProgress(liveCoop.isInProgress())
                    .build();
        }
    }

//    @Getter
//    @Builder
//    @NoArgsConstructor(access = AccessLevel.PRIVATE)
//    @AllArgsConstructor(access = AccessLevel.PRIVATE)
//    public static class GetLiveCoopInfo {
//        private int id;
//        private int userId;
//        private String userName;
//        private String userImgUrl;
//        private int maxMemNum;
//        private int nowMemNum;
//        private int duration;
//        private String title;
//        private boolean inProgress;
//
//        public static GetLiveCoopInfo build(LiveCoop liveCoop) {
//            return GetLiveCoopInfo.builder()
//                    .id(liveCoop.getId())
//                    .userId(liveCoop.getUser().getId())
//                    .userName(liveCoop.getUser().getNickname())
//                    .userImgUrl(liveCoop.getUser().getImgUrl())
//                    .maxMemNum(liveCoop.getMemNum())
//                    .nowMemNum(liveCoop.getNowMemNum())
//                    .duration(liveCoop.getDuration())
//                    .title(liveCoop.getTitle())
//                    .inProgress(liveCoop.isInProgress())
//                    .build();
//        }
//    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveCoops {
        private List<GetLiveCoop> liveCoops;

        public static LiveCoopResponse.GetLiveCoops build(List<LiveCoop> liveCoops) {
            return LiveCoopResponse.GetLiveCoops.builder()
                    .liveCoops(liveCoops.stream().map(LiveCoopResponse.GetLiveCoop::build).collect(Collectors.toList()))
                    .build();
        }
    }


}
