package com.cogether.api.project.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_Project{
        private int userId;
        private LocalDateTime start;
        private int total_mem;
        private int cur_mem;
        private String online;
        private String title;
        private String content;
        private List<String> skillList;
        public Project toEntity(User user){
            return Project.builder()
                    .user(user)
                    .start(start)
                    .totalMem(total_mem)
                    .curMem(cur_mem)
                    .online(online)
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create_ProjectScrap{
        private int projectId;
        private int userId;

        public ProjectScrap toEntity(Project project, User user){
            return ProjectScrap.builder()
                    .project(project)
                    .user(user)
                    .build();
        }
    }
}
