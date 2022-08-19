package com.cogether.api.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSkillResponse {

    private int userSkillId;
    private String skillName;
}
