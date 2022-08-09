package com.cogether.api.user.dto;

import com.cogether.api.user.domain.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSkillResponse {

    private int id;
    private int userId;
    private String skillId;
}
