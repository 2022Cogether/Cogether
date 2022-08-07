package com.cogether.api.follow.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequest {
    int toId;
    int fromId;
}
