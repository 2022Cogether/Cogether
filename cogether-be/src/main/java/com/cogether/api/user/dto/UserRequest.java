package com.cogether.api.user.dto;

import lombok.*;

import java.util.List;


//로그인
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private String intro;

    private String git_url;
    private String notion_url;
    private String tistory_url;
    private String etc_url;

    private String img_url;

    private boolean admin;
    private boolean verified;

    private List<String> skills;
}
