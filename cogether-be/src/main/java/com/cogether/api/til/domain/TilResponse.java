package com.cogether.api.til.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TilResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId{
        private int id;

        public static TilResponse.OnlyId build(Til til){
            return OnlyId.builder()
                    .id(til.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyLikeId{
        private int id;

        public static TilResponse.OnlyLikeId build(TilLike tilLike){
            return OnlyLikeId.builder()
                    .id(tilLike.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyCommentId{
        private int id;

        public static TilResponse.OnlyCommentId build(TilComment tilComment){
            return OnlyCommentId.builder()
                    .id(tilComment.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class getTilImg{
        private int tilImgId;
        private int tilId;
        private String imgUrl;

        public static TilResponse.getTilImg build(TilImg tilImg){
            return getTilImg.builder()
                    .tilImgId(tilImg.getId())
                    .tilId(tilImg.getTil().getId())
                    .imgUrl(tilImg.getUrl())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class getTilComment{
        private int tilCommentId;
        private int userId;
        private String userImg;
        private String userNickname;
        private String content;
        private LocalDateTime createdAt;

        public static TilResponse.getTilComment build(TilComment tilComment){
            return getTilComment.builder()
                    .tilCommentId(tilComment.getId())
                    .userId(tilComment.getUser().getId())
                    .userImg(tilComment.getUser().getImgUrl())
                    .userNickname(tilComment.getUser().getNickname())
                    .content(tilComment.getContent())
                    .createdAt(tilComment.getCreatedAt())
                    .build();

        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TilAll{
        private int tilId;
        private String tilTitle;
        private String tilContent;
        private int userId;
        private String userImg;
        private String userNickname;
        private LocalDateTime createdAt;
        private List<TilResponse.getTilImg> imgUrl;
        private List<TilResponse.getTilComment> commentList;
        private int likeCnt;
        private boolean isLike;

        public static TilResponse.TilAll build(Til til, TilLike tilLike, List<TilImg> tilImg, List<TilComment> tilComment, int likeCnt, boolean isLike){
            return TilAll.builder()
                    .tilId(til.getId())
                    .tilTitle(til.getTitle())
                    .tilContent(til.getContent())
                    .userId(til.getUser().getId())
                    .userImg(til.getUser().getImgUrl())
                    .userNickname(til.getUser().getNickname())
                    .createdAt(til.getCreatedAt())
                    .imgUrl(tilImg.stream().map(getTilImg::build).collect(Collectors.toList()))
                    .commentList(tilComment.stream().map(getTilComment::build).collect(Collectors.toList()))
                    .likeCnt(likeCnt)
                    .isLike(isLike)
                    .build();
        }
    }
}
