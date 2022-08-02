package com.cogether.api.liveComp.domain;

import com.cogether.api.hunting.domain.HuntingResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class LiveCompResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private long id;

        public static LiveCompResponse.OnlyId build(LiveComp liveComp) {
            return LiveCompResponse.OnlyId.builder()
                    .id(liveComp.getId())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLiveComp {
        private int id;
        private int userId;
        private LocalTime totalTime;
        private int ranking;
        private int cnt;

        public static LiveCompResponse.GetLiveComp build(LiveComp liveComp, int ranking, int cnt) {
            return GetLiveComp.builder()
                    .id(liveComp.getId())
                    .userId(liveComp.getUser().getId())
                    .totalTime(liveComp.getTotalTime())
                    .ranking(ranking)
                    .cnt(cnt)
                    .build();
        }
    }

}
