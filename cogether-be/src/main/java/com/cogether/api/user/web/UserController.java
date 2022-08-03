package com.cogether.api.user.web;

import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//1. 회원가입
//2. 로그인



@RequiredArgsConstructor
@RestController
@RequestMapping(path="api")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity signUp(@RequestBody UserRequest userRequest) {
        return userService.findByUserId(userRequest.getUserEmail()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userRequest));
    }

    //로그인인
   @PostMapping("/user/signIn")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) throws Exception {

        return ResponseEntity.ok().body(userService.signIn(userRequest));
    }


}
