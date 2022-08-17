package com.cogether.api.email.dto;

import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
    private int id;

    private String email;

    private String code;
}
