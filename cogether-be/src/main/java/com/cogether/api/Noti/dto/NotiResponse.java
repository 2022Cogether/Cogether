package com.cogether.api.Noti.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotiResponse {

    private int sendId;
    private int receiveId;
    private String type;
    private int contentId;
    private String url;
    private LocalDateTime createAt;
    private LocalDateTime readAt;
}
