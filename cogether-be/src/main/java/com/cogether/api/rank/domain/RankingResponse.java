package com.cogether.api.rank.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.util.List;

public class RankingResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TilRankList {
        private int myRank;
        private String userNickname;
        private String userImg;
        private int myTilCnt;
        private List<TilRank> rankList;

        public static RankingResponse.TilRankList build(int myRank, User user, int myTilCnt, List<TilRank> tilRankList) {
            return TilRankList.builder()
                    .myRank(myRank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .myTilCnt(myTilCnt)
                    .rankList(tilRankList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TilRank {
        private int rank;
        private String userNickname;
        private String userImg;
        private int content;

        public static RankingResponse.TilRank build(int rank, User user, int tilCnt) {
            return TilRank.builder()
                    .rank(rank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .content(tilCnt)
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExpRankList {
        private int myRank;
        private String userNickname;
        private String userImg;
        private int myExp;
        private List<ExpRank> rankList;

        public static RankingResponse.ExpRankList build(int myRank, User user, List<ExpRank> expRankList) {
            return ExpRankList.builder()
                    .myRank(myRank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .myExp(user.getExp())
                    .rankList(expRankList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExpRank {
        private int rank;
        private String userNickname;
        private String userImg;
        private int content;

        public static RankingResponse.ExpRank build(int rank, User user) {
            return ExpRank.builder()
                    .rank(rank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .content(user.getExp())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LiveCompRankList {
        private int myRank;
        private String userNickname;
        private String userImg;
        private int myLiveComp;
        private List<LiveCompRank> rankList;

        public static RankingResponse.LiveCompRankList build(int myRank, User user, int myLiveComp, List<LiveCompRank> liveCompRankList) {
            return LiveCompRankList.builder()
                    .myRank(myRank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .myLiveComp(myLiveComp)
                    .rankList(liveCompRankList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LiveCompRank {
        private int rank;
        private String userNickname;
        private String userImg;
        private int content;

        public static RankingResponse.LiveCompRank build(int rank, User user, int liveComp) {
            return LiveCompRank.builder()
                    .rank(rank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .content(liveComp)
                    .build();
        }
    }
}
