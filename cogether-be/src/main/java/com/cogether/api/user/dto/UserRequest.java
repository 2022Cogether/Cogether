package com.cogether.api.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private String userEmail;
    private String userPassword;
}
