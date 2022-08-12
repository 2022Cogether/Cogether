package com.cogether.api.follow.web;

import com.cogether.api.follow.dto.FollowRequest;
import com.cogether.api.follow.service.FollowingService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 1.팔로우 등록
 * 2.팔로우 해제
 * 3.내 팔로잉 목록 보기
 * 4.내 팔로워 목록 보기
 */



@RequiredArgsConstructor
@RestController
@RequestMapping(path="api")
public class FollowingController {

    private final FollowingService followingService;

    @PostMapping("/follow")
    public ResponseEntity registFollow(@RequestBody FollowRequest followRequest)
    {
        followingService.registFollow(followRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/follow")
    public ResponseEntity resignFollow(@RequestBody FollowRequest followRequest)
    {
        followingService.resignFollow(followRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/following")
    public ResponseEntity loadFollowingList(@RequestHeader("ACCESS_TOKEN")String accessToken)
    {
        return ResponseEntity.ok().body(followingService.loadFollowingList(accessToken));
    }

    @GetMapping("/follower")
    public ResponseEntity loadFollowerList(@RequestHeader("ACCESS_TOKEN")String accessToken)
    {
        return ResponseEntity.ok().body(followingService.loadFollowerList(accessToken));
    }
}
