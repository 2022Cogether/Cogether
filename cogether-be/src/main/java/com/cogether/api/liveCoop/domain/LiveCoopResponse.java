package com.cogether.api.liveCoop.domain;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class LiveCoopResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SocketLiveCoop {
        private int mode;

        public static LiveCoopResponse.SocketLiveCoop build(int mode) {
            return LiveCoopResponse.SocketLiveCoop.builder()
                    .mode(mode)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyLiveCoopId {
        private int liveCoopId;

        public static LiveCoopResponse.OnlyLiveCoopId build(LiveCoop liveCoop) {
            return OnlyLiveCoopId.builder()
                    .liveCoopId(liveCoop.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LiveCoopChatRoomLiveCoopMemberId {
        private int liveCoopId;
        private int chatRoomId;
        private int liveCoopMemberId;

        public static LiveCoopResponse.LiveCoopChatRoomLiveCoopMemberId build(int liveCoopId, int chatRoomId, int liveCoopMemberId) {
            return LiveCoopChatRoomLiveCoopMemberId.builder()
                    .liveCoopId(liveCoopId)
                    .chatRoomId(chatRoomId)
                    .liveCoopMemberId(liveCoopMemberId)
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
        private int chatRoomId;
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
                    .chatRoomId(liveCoop.getChatRoom().getId())
                    .inProgress(liveCoop.isInProgress())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveCoops {
        private List<GetLiveCoop> liveCoops;
        private boolean enterCoop;

        public static LiveCoopResponse.GetLiveCoops build(List<LiveCoop> liveCoops, boolean enterCoop) {
            return LiveCoopResponse.GetLiveCoops.builder()
                    .enterCoop(enterCoop)
                    .liveCoops(liveCoops.stream().map(LiveCoopResponse.GetLiveCoop::build).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyLiveCoopMemberId {
        private int liveCoopMemberId;

        public static LiveCoopResponse.OnlyLiveCoopMemberId build(LiveCoopMember liveCoopMember) {
            return OnlyLiveCoopMemberId.builder()
                    .liveCoopMemberId(liveCoopMember.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveCoopMember {
        private int liveCoopMemberId;
        private int userId;
        private String userName;
        private String userImgUrl;
        private String code;

        public static GetLiveCoopMember build(LiveCoopMember liveCoopMember) {
            return GetLiveCoopMember.builder()
                    .liveCoopMemberId(liveCoopMember.getId())
                    .userId(liveCoopMember.getUser().getId())
                    .userName(liveCoopMember.getUser().getNickname())
                    .userImgUrl(liveCoopMember.getUser().getImgUrl())
                    .code(liveCoopMember.getCode())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveCoopMembers {
        private List<GetLiveCoopMember> liveCoopMembers;
        private int myLiveCoopMemberId;

        public static LiveCoopResponse.GetLiveCoopMembers build(List<LiveCoopMember> liveCoopMembers, LiveCoopMember liveCoopMember) {
            return LiveCoopResponse.GetLiveCoopMembers.builder()
                    .liveCoopMembers(liveCoopMembers.stream().map(LiveCoopResponse.GetLiveCoopMember::build).collect(Collectors.toList()))
                    .myLiveCoopMemberId(liveCoopMember.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LiveCoopState {
        private int liveCoopId;
        private boolean inProgress;

        public static LiveCoopResponse.LiveCoopState build(LiveCoop liveCoop) {
            return LiveCoopState.builder()
                    .liveCoopId(liveCoop.getId())
                    .inProgress(liveCoop.isInProgress())
                    .build();
        }
    }

}
