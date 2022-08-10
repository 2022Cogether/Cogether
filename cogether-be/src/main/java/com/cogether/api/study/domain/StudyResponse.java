package com.cogether.api.study.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StudyResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId{
        private int id;

        public static StudyResponse.OnlyId build(Study study){
            return OnlyId.builder()
                    .id(study.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class getStudySkill{
        private int studySkillId;
        private int studyId;
        private String skillName;

        public static StudyResponse.getStudySkill build(StudySkill studySkill){
            return getStudySkill.builder()
                    .studySkillId(studySkill.getId())
                    .studyId(studySkill.getStudy().getId())
                    .skillName(studySkill.getSkillName())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StudyAll{
        private int studyId;
        private int userId;
        private String userNickname;
        private String userImgurl;
        private LocalDateTime start;
        private int total_mem;
        private int cur_mem;
        private String online;
        private String title;
        private String content;
        private List<StudyResponse.getStudySkill> skillList;
        private LocalDateTime createdAt;
        private int scrapId;
        private boolean isScrap;

        public static StudyResponse.StudyAll build(Study study, List<StudySkill> studySkill,int scrapId, boolean isScrap){
            return StudyAll.builder()
                    .studyId(study.getId())
                    .userNickname(study.getUser().getNickname())
                    .userImgurl(study.getUser().getImgUrl())
                    .userId(study.getUser().getId())
                    .start(study.getStart())
                    .total_mem(study.getTotalMem())
                    .cur_mem(study.getCurMem())
                    .online(study.getOnline())
                    .title(study.getTitle())
                    .content(study.getContent())
                    .skillList(studySkill.stream().map(getStudySkill::build).collect(Collectors.toList()))
                    .createdAt(study.getCreatedAt())
                    .scrapId(scrapId)
                    .isScrap(isScrap)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StudyList{
        private List<StudyResponse.StudyAll> studyList;

        public static StudyResponse.StudyList build(List<StudyResponse.StudyAll> studyAll){
            return StudyList.builder()
                    .studyList(studyAll)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyStudyScrapId{
        private int id;

        public static StudyResponse.OnlyStudyScrapId build(StudyScrap studyScrap){
            return OnlyStudyScrapId.builder()
                    .id(studyScrap.getId())
                    .build();
        }
    }
}
