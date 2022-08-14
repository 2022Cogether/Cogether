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

    @PostMapping(value="/follow/{userId}",headers = "ACCESS_TOKEN")
    public ResponseEntity registFollow(@PathVariable("userId")int Id,@RequestHeader("ACCESS_TOKEN")String token)
    {
        followingService.registFollow(Id,token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/follow/{userId}",headers = "ACCESS_TOKEN")
    public ResponseEntity resignFollow(@PathVariable("userId")int Id,@RequestHeader("ACCESS_TOKEN") String token)
    {
        followingService.resignFollow(Id,token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/following/{userId}")
    public ResponseEntity loadFollowingList(@PathVariable("userId")int fromId)
    {
        return ResponseEntity.ok().body(followingService.loadFollowingList(fromId));
    }

    @GetMapping(value = "/following/lists/{userId}")
    public ResponseEntity loadFollowerList(@PathVariable("userId")int fromId)
    {
        return ResponseEntity.ok().body(followingService.loadFollowingListSize(fromId));
    }

    @GetMapping(value = "/follower/{userId}")
    public ResponseEntity loadFollowingListSize(@PathVariable("userId")int fromId)
    {
        return ResponseEntity.ok().body(followingService.loadFollowerList(fromId));
    }

    @GetMapping(value = "/follower/lists/{userId}")
    public ResponseEntity loadFollowerListSize(@PathVariable("userId")int fromId)
    {
        return ResponseEntity.ok().body(followingService.loadFollowerListSize(fromId));
    }



}
