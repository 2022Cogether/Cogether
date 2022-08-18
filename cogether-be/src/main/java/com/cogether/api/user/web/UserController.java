package com.cogether.api.user.web;

import com.cogether.api.til.service.TilService;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 회원가입
 * 2. 로그인
 * 3. 로그아웃
 * 4. 비밀번호 검증 (verify/password
 */


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;
    private final TilService tilService;

    /**
     * 회원가입
     */
    @PostMapping("/sign/signup")
    public ResponseEntity signUp(@RequestBody UserRequest userRequest) {

        if(userService.findByEmail(userRequest.getEmail()).isPresent())
        {
            return ResponseEntity.badRequest().build();
        }
        else
        {
            TokenResponse body = userService.signUp(userRequest);

            return ResponseEntity.ok(body);
        }



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
    @DeleteMapping(value = "/sign/signout", headers = "ACCESS_TOKEN")
    public ResponseEntity signOut(@RequestHeader("ACCESS_TOKEN") String token) throws Exception {
        System.out.println("로그아웃");
        return ResponseEntity.ok().body(userService.signOut(token));
    }


    /**
     * 이메일 중복확인
     * true : 중복 false : 중복 x
     */
    @GetMapping("/verify/email/{email}")
    public ResponseEntity verifyDuplicationOfEmail(@PathVariable("email") String email) throws Exception {

        boolean emailIsPresent = userService.verifyDuplicationOfEmail(email);


        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("duplicate", String.valueOf(emailIsPresent));

        //return ResponseEntity.ok().body(body);
        return emailIsPresent ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
    }


    /**
     * 닉네임 중복확인
     * true : 중복 false : 중복 x
     */
    @GetMapping("/verify/nickname/{nickname}")
    public ResponseEntity verifyDuplicationOfNickName(@PathVariable("nickname") String nickname) throws Exception {

        boolean nickNameIsPresent = userService.verifyDuplicationOfNickName(nickname);

        return nickNameIsPresent ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
    }

    /**
     * 유저 정보 조회
     */
    @GetMapping(value = "/user/info/{userId}")
    public ResponseEntity findUser(@PathVariable("userId") int userId) throws Exception {

        return ResponseEntity.ok().body(userService.findUserInfo(userId));
    }

    /**
     * 유저 정보 변경
     */
    @PutMapping(value = "/user/info", headers = "ACCESS_TOKEN")
    public ResponseEntity modifyUserInfo(@RequestBody UserRequest userRequest, @RequestHeader("ACCESS_TOKEN") String token) throws Exception {

        return ResponseEntity.ok().body(userService.modifyUserInfo(userRequest, token));
    }

    /**
     * 회원 탈퇴
     */
    @PutMapping(value = "/user/resign", headers = "ACCESS_TOKEN")
    public ResponseEntity resignUser(@RequestHeader("ACCESS_TOKEN") String token) throws Exception {
        Map<String, Object> body = new HashMap<>();

        int id = userService.resignUser(token);
        body.put("id", id);
        body.put("resign", true);

        return ResponseEntity.ok().body(body);
    }

    /**
     * 비밀번호 변경
     */
    @PutMapping("/user/password")
    public ResponseEntity modifyPassword(@RequestBody UserRequest userRequest) throws Exception {
        Map<String, String> body = new HashMap<>();

        userService.modifyPassword(userRequest);

        body.put("modify", "true");
        return ResponseEntity.ok().body(body);
    }

    /**
     * 엑세스토큰 재발급
     */
    @PostMapping(value = "/sign/token/{userId}", headers = "REFRESH_TOKEN")
    public ResponseEntity reissuanceAccessToken(@RequestHeader("REFRESH_TOKEN") String token, @PathVariable("userId") int id) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(token);
        TokenResponse tokenResponse = userService.reissuanceAccessToken(token, id);
        headers.set("ACCESS_TOKEN", tokenResponse.getACCESS_TOKEN());
        headers.set("REFRESH_TOKEN", tokenResponse.getREFRESH_TOKEN());

        return ResponseEntity.ok().headers(headers).build();
    }

    /**
     * 프로필 이미지 서버 업로드
     */
    @PostMapping(value = "user/info/profileimg", headers = "ACCESS_TOKEN")
    public ResponseEntity uploadProfileImg(@RequestHeader("ACCESS_TOKEN") String token, @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok().body(userService.uploadProfileImg(token, multipartFile));
    }

    @GetMapping(value = "verify/password", headers = "ACCESS_TOKEN")
    public ResponseEntity verifyPassword(@RequestHeader("ACCESS_TOKEN") String token, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(userService.verifyPassword(userRequest, token));
    }

    /**
     * 닉네임 검색
     */
    @GetMapping(value = "find/nickname/{nickname}")
    public ResponseEntity findNickname(@PathVariable("nickname") String nickname) {

        return ResponseEntity.ok().body(userService.findNickName(nickname));
    }

    /**
     * 이메일 아이디 검색
     */

    @GetMapping(value = "find/email/{email}")
    public ResponseEntity findEmail(@PathVariable("email") String email) {

        return ResponseEntity.ok().body(userService.findEmail(email));
    }
}
