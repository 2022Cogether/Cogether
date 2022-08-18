package com.cogether.api.Noti.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotiResponse {

    private int sendId;   //피유저
    private int receiveId; //알림 생성자

    private int userId;
    private String type;
    private int contentId;
    private String url;
    private LocalDateTime createAt;
    private LocalDateTime readAt;
}
