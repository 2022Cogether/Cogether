package com.cogether.api.liveComp.domain;

import lombok.*;

import java.time.LocalTime;

public class LiveCompRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create {
        private int userId;
        private LocalTime plusTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Update {
        private int userId;
        private LocalTime plusTime;
    }

}
