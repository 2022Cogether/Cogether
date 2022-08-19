package com.cogether.api.liveComp.domain;

import lombok.*;

public class LiveCompRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Update {
        private int userId;
    }

}
