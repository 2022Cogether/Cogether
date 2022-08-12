package com.cogether.api.user.web;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.user.dto.LoginRequest;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.service.SkillService;
import com.cogether.api.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 회원가입
 * 2. 로그인
 * 3. 로그아웃
 */



@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api")
public class UserController {

    private final UserService userService;

    private final TokenUtils tokenUtils;

    /**
     * 회원가입
     */
    @PostMapping("/sign/signup")
    public ResponseEntity signUp(@RequestBody UserRequest userRequest) {
        return userService.findByEmail(userRequest.getEmail()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userRequest));
    }

    /**
     * 로그인
     */
   @PostMapping("/sign/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) throws Exception {

        return ResponseEntity.ok().body(userService.signIn(userRequest));
    }

    /**
     * 로그아웃
     */
    @GetMapping("/sign/signout/{id}")
    public ResponseEntity signOut(@PathVariable("id") int userId) throws Exception
    {
        userService.signOut(userId);
        System.out.println("로그아웃");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //이메일 인증


    //이메일 중복확인      true : 중복 false : 중복 x
    @GetMapping("/verify/email/{email}")
    public ResponseEntity  verifyDuplicationOfEmail(@PathVariable("email") String email) throws Exception {

        boolean emailIsPresent =userService.verifyDuplicationOfEmail(email);


        Map<String,String> body =new HashMap<>();
        body.put("email",email);
        body.put("duplicate",String.valueOf(emailIsPresent));

        //return ResponseEntity.ok().body(body);
        return emailIsPresent? ResponseEntity.ok().body(true) :ResponseEntity.ok().body(false);
    }


    //닉네임 중복확인      true : 중복 false : 중복 x
    @GetMapping("/verify/nickname/{nickname}")
    public ResponseEntity verifyDuplicationOfNickName(@PathVariable("nickname") String nickname) throws Exception {

        boolean nickNameIsPresent = userService.verifyDuplicationOfNickName(nickname);

//        Map<String,String> body =new HashMap<>();
//        body.put("nickname",nickname);
//        body.put("duplicate",String.valueOf(nickNameIsPresent));
//
//        //String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);

        return nickNameIsPresent? ResponseEntity.ok().body(true) :ResponseEntity.ok().body(false);
    }

    /**
     * 유저 정보 조회
     */
    @GetMapping(value = "/user/{userId}",headers = "ACCESS_TOKEN")
    public ResponseEntity findUser(@PathVariable int userId, @RequestHeader("ACCESS_TOKEN") String data) throws Exception{



        System.out.println("헤더값 가져오기"+data);
        int id =tokenUtils.getUserIdFromToken(data);
        System.out.println("userid : "+id);

        return ResponseEntity.ok().body(userService.findUserInfo(userId));
    }

    /**
     * 유저 정보 변경
     */
    @PutMapping("/user")
    public ResponseEntity modifyUserInfo(@RequestBody UserRequest userRequest) throws Exception
    {
        return ResponseEntity.ok().body(userService.modifyUserInfo(userRequest));
    }

    /**
     * 회원 탈퇴
     */
    @PutMapping("/user/resign")
    public  ResponseEntity resignUser(@RequestBody UserRequest userRequest) throws  Exception
    {
        Map<String,String> body = new HashMap<>();

        userService.resignUser(userRequest);
        body.put("id",Integer.toString(userRequest.getId()));
        body.put("resign","true");

        return ResponseEntity.ok().body(body);
    }

    /**
     * 비밀번호 변경
     */
    @PutMapping("/user/password")
    public  ResponseEntity modifyPassword(@RequestBody UserRequest userRequest) throws Exception
    {
        Map<String,String> body = new HashMap<>();

        userService.modifyPassword(userRequest);

        body.put("modify","true");
        return ResponseEntity.ok().body(body);
    }

    /**
     * 엑세스토큰 재발급
     */
    @PostMapping("/sign/token")
    public  ResponseEntity reissuanceAccessToken(@RequestBody UserRequest userRequest) throws Exception
    {
        return ResponseEntity.ok().body(userService.reissuanceAccessToken(userRequest));
    }


}
