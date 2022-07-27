package com.cogether.api.til.domain;

import com.cogether.api.user.dto.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class TilRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_Til{
        private String title;
        private String content;
        private int userId;

        public Til toEntity(User user){
            return Til.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_TilImg{
        private List<MultipartFile> imgList;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Update_Til{
        private int tilId;
        private String title;
        private String content;

        public Til toEntity(){
            return Til.builder()
                    .id(tilId)
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_TilComment{
        private String content;
        private int userId;

        public TilComment toEntity(User user){
            return TilComment.builder()
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Update_TilComment{
        private int tilCommentId;
        private String content;
        private int userId;

        public TilComment toEntity(User user){
            return TilComment.builder()
                    .id(tilCommentId)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_TilLike{
        private int tilId;
        private int userId;

        public TilLike toEntity(Til til, User user){
            return TilLike.builder()
                    .til(til)
                    .user(user)
                    .build();
        }
    }
}
