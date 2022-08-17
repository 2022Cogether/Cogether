package com.cogether.api.project.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private int id;

        public static ProjectResponse.OnlyId build(Project project) {
            return OnlyId.builder()
                    .id(project.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class getProjectSkill {
        private int projectSkillId;
        private int projectId;
        private String skillName;

        public static ProjectResponse.getProjectSkill build(ProjectSkill projectSkill) {
            return getProjectSkill.builder()
                    .projectSkillId(projectSkill.getId())
                    .projectId(projectSkill.getProject().getId())
                    .skillName(projectSkill.getSkillName())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProjectAll {
        private int projectId;
        private int userId;
        private String userNickname;
        private String userImgurl;
        private LocalDateTime start;
        private int total_mem;
        private int cur_mem;
        private String online;
        private String title;
        private String content;
        private List<ProjectResponse.getProjectSkill> skillList;
        private LocalDateTime createdAt;
        private int scrapId;
        private boolean isScrap;

        public static ProjectResponse.ProjectAll build(Project project, List<ProjectSkill> projectSkill, int scrapId, boolean isScrap) {
            return ProjectAll.builder()
                    .projectId(project.getId())
                    .userNickname(project.getUser().getNickname())
                    .userImgurl(project.getUser().getImgUrl())
                    .userId(project.getUser().getId())
                    .start(project.getStart())
                    .total_mem(project.getTotalMem())
                    .cur_mem(project.getCurMem())
                    .online(project.getOnline())
                    .title(project.getTitle())
                    .content(project.getContent())
                    .skillList(projectSkill.stream().map(getProjectSkill::build).collect(Collectors.toList()))
                    .createdAt(project.getCreatedAt())
                    .scrapId(scrapId)
                    .isScrap(isScrap)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProjectList {
        private List<ProjectResponse.ProjectAll> projectList;

        public static ProjectResponse.ProjectList build(List<ProjectResponse.ProjectAll> projectAll) {
            return ProjectList.builder()
                    .projectList(projectAll)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyProjectScrapId {
        private int id;

        public static ProjectResponse.OnlyProjectScrapId build(ProjectScrap projectScrap) {
            return OnlyProjectScrapId.builder()
                    .id(projectScrap.getId())
                    .build();
        }
    }
}