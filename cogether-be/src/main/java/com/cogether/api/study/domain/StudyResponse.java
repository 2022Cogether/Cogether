package com.cogether.api.study.domain;

import lombok.*;

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
}
