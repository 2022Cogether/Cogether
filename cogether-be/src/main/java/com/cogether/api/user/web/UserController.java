package com.cogether.api.user.web;

import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return userService.findByEmail(userRequest.getEmail()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userRequest));
    }

    //로그인
   @PostMapping("/user/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) throws Exception {

        return ResponseEntity.ok().body(userService.signIn(userRequest));
    }

    //로그아웃
    @GetMapping("/user/signout")
    public ResponseEntity signOut(@RequestBody UserRequest userRequest) throws Exception
    {
        userService.signOut(userRequest);
        System.out.println("로그아웃");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //이메일 인증
    @PostMapping("user/verify")

//    @GetMapping("/user/verify")
//    public ResponseEntity<Boolean> verifyDuplicationOfEmail(@RequestBody UserRequest userRequest) throws Exception {
//
//        return ResponseEntity.ok().body(userService.verifyDuplicationOfEmail(userRequest));
//    }

    //이메일 중복확인      true : 중복 false : 중복 x
    @GetMapping("/user/verify/{email}")
    public ResponseEntity<Boolean> verifyDuplicationOfEmail(@PathVariable String email) throws Exception {

        return ResponseEntity.ok().body(userService.verifyDuplicationOfEmail(email));
    }

    //닉네임 중복확인      true : 중복 false : 중복 x
    @GetMapping("/user/verify/{nickname}")
    public ResponseEntity<Boolean> verifyDuplicationOfNickName(@PathVariable String nickname) throws Exception {

        return ResponseEntity.ok().body(userService.verifyDuplicationOfNickName(nickname));
    }

    //회원정보 변경
    @PutMapping("/user")
    public ResponseEntity<TokenResponse> modifyUserInfo(@RequestBody UserRequest userRequest) throws Exception
    {
        return ResponseEntity.ok().body(userService.modifyUserInfo(userRequest));
    }



}
