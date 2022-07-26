package com.cogether.api.hunting.dto;

import lombok.*;

public class HuntingResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private long id;

        public static HuntingResponse.OnlyId build(Hunting hunting) {
            return HuntingResponse.OnlyId.builder()
                    .id(hunting.getId())
                    .build();
        }
    }
}
