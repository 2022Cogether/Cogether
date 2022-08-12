package com.cogether.api.Noti.web;

import com.cogether.api.Noti.dto.NotiRequest;
import com.cogether.api.Noti.service.NotiService;
import com.cogether.api.config.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 1. 알림 등록
 * 2. 알림 리스트 조회
 * 3. 알림 읽음 처리
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path="api")
public class NotiController {

    private NotiService notiService;
    private final TokenUtils tokenUtils;

    /**
     * 알림등록
     */
    @PostMapping(value = "/noti",headers = "ACCESS_TOKEN")
    public ResponseEntity postNoti(@RequestBody NotiRequest notiRequest, @RequestHeader("ACCESS_TOKEN") String data) throws Exception{

        int id =tokenUtils.getUserIdFromToken(data);
        notiRequest.setSendId(id);

        return ResponseEntity.ok().body(notiService.registerNoti(notiRequest));
    }

    /**
     * 해당유저 알림리스트 가져오기
     */
    @GetMapping(value = "/noti/list",headers = "ACCESS_TOKEN")
    public ResponseEntity getNotiList( @RequestHeader("ACCESS_TOKEN") String data) throws Exception{

        int id =tokenUtils.getUserIdFromToken(data);


        return ResponseEntity.ok().body(notiService.getUserNoti(id));
    }

    @PutMapping(value = "/noti",headers = "ACCESS_TOKEN")
    public ResponseEntity getNotiList(@RequestBody NotiRequest notiRequest, @RequestHeader("ACCESS_TOKEN") String data) throws Exception{

        int id =tokenUtils.getUserIdFromToken(data);
        notiRequest.setSendId(id);


        return ResponseEntity.ok().body(notiService.readNoti(notiRequest));
    }
}
