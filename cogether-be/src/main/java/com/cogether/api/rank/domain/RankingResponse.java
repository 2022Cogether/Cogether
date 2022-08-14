package com.cogether.api.rank.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class RankingResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TilRankList{
        private int myRank;
        private String userNickname;
        private String userImg;
        private int myTilCnt;
        private List<TilRank> tilRankList;

        public static RankingResponse.TilRankList build(int myRank, User user, int myTilCnt, List<TilRank> tilRankList){
            return TilRankList.builder()
                    .myRank(myRank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .myTilCnt(myTilCnt)
                    .tilRankList(tilRankList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TilRank{
        private int rank;
        private String userNickname;
        private String userImg;
        private int tilCnt;

        public static RankingResponse.TilRank build(int rank, User user, int tilCnt){
            return TilRank.builder()
                    .rank(rank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .tilCnt(tilCnt)
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExpRankList{
        private int myRank;
        private String userNickname;
        private String userImg;
        private int myExp;
        private List<ExpRank> expRankList;

        public static RankingResponse.ExpRankList build(int myRank, User user, List<ExpRank> expRankList){
            return ExpRankList.builder()
                    .myRank(myRank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .myExp(user.getExp())
                    .expRankList(expRankList)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExpRank{
        private int rank;
        private String userNickname;
        private String userImg;
        private int Exp;

        public static RankingResponse.ExpRank build(int rank, User user){
            return ExpRank.builder()
                    .rank(rank)
                    .userNickname(user.getNickname())
                    .userImg(user.getImgUrl())
                    .Exp(user.getExp())
                    .build();
        }
    }
}
