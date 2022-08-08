package com.cogether.api.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {

    private int userId;
    private String ACCESS_TOKEN;
    private String REFRESH_TOKEN;
}
