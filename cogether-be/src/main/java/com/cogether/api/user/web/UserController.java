package com.cogether.api.user.web;

import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 1. 회원가입
 * 2. 로그인
 * 3. 로그아웃
 */



@RequiredArgsConstructor
@RestController
@RequestMapping(path="api")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/sign/signup")
    public ResponseEntity signUp(@RequestBody UserRequest userRequest) {
        return userService.findByEmail(userRequest.getEmail()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userRequest));
    }

    //로그인
   @PostMapping("/sign/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) throws Exception {

        return ResponseEntity.ok().body(userService.signIn(userRequest));
    }

    //로그아웃
    @GetMapping("/sign/signout")
    public ResponseEntity signOut(@RequestBody UserRequest userRequest) throws Exception
    {
        userService.signOut(userRequest);
        System.out.println("로그아웃");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //이메일 인증
    @PostMapping("user/verify")

    //이메일 중복확인      true : 중복 false : 중복 x
    @GetMapping("/user/verify")
    public ResponseEntity verifyDuplicationOfEmail(@RequestBody UserRequest userRequest) throws Exception {

        boolean emailIsPresent =userService.verifyDuplicationOfEmail(userRequest);
        String duplicate ="";

        return emailIsPresent ? ResponseEntity.ok().body(duplicate="true")
                :ResponseEntity.ok().body(duplicate="false");
    }


    //닉네임 중복확인      true : 중복 false : 중복 x
    @GetMapping("/user/verify/{nickname}")
    public ResponseEntity verifyDuplicationOfNickName(@RequestParam String nickname) throws Exception {

        boolean nickNameIsPresent = userService.verifyDuplicationOfNickName(nickname);
        String duplicate ="";

        return nickNameIsPresent ? ResponseEntity.ok().body(duplicate="true")
                :ResponseEntity.ok().body(duplicate="false");
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity findUser(@RequestParam int userId) throws Exception{

        return ResponseEntity.ok().body(userService.findUserInfo(userId));
    }


    //회원정보 변경
    @PutMapping("/user")
    public ResponseEntity<TokenResponse> modifyUserInfo(@RequestBody UserRequest userRequest) throws Exception
    {
        return ResponseEntity.ok().body(userService.modifyUserInfo(userRequest));
    }




}
