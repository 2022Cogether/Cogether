package com.cogether.api.Noti.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotiRequest {

    int toId;
    int fromId;
    String message;
}
