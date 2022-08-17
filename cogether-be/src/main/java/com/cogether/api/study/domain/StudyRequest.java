package com.cogether.api.study.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class StudyRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_Study {
        private int userId;
        private LocalDateTime start;
        private int total_mem;
        private int cur_mem;
        private String online;
        private String title;
        private String content;
        private List<String> skillList;

        public Study toEntity(User user) {
            return Study.builder()
                    .user(user)
                    .start(start)
                    .totalMem(total_mem)
                    .curMem(cur_mem)
                    .online(online)
                    .title(title)
                    .content(content)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_StudyScrap {
        private int studyId;
        private int userId;

        public StudyScrap toEntity(Study study, User user) {
            return StudyScrap.builder()
                    .study(study)
                    .user(user)
                    .build();
        }
    }
}
