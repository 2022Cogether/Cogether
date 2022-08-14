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

}
