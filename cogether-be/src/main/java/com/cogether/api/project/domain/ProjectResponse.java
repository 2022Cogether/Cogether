package com.cogether.api.project.domain;

import lombok.*;

public class ProjectResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId{
        private int id;

        public static ProjectResponse.OnlyId build(Project project){
            return OnlyId.builder()
                    .id(project.getId())
                    .build();
        }
    }

}