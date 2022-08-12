package com.cogether.api.Noti.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotiRequest {

    private int sendId;   //피유저
    private int recieveId; //알림 생성자
    //private String message; // 알림내용     <-- 얘 없어두 댈듯\
    private String url;
    private String type ; // 알림종류   comment,like,DM,follow
    private int contentId; // 알림해당 글 식별자

}
