package com.cogether.api.email.web;

import com.cogether.api.email.dto.EmailRequest;
import com.cogether.api.email.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 회원가입 이메일 인증
 * 2. 임시비밀번호 발급
 * 3. 이메일인증 검증
 */


@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api")
public class EmailController {

    private final EmailService emailService;


    /**
     * 회원가입 이메일 인증 코드 전송
     */
    @PostMapping("/sign/verify")
    public ResponseEntity emailConfirm(@RequestBody EmailRequest emailRequest) throws Exception {

        System.out.println("이메일 보낼꺼얌 " + emailRequest.getEmail());
        String email = emailRequest.getEmail();
        String confirm = emailService.sendSimpleMessage(email);

        return ResponseEntity.ok().body("confirm");
    }

    /**
     * 인증코드 검증
     */
    @GetMapping("/sign/verify")
    public ResponseEntity emailVerificationSuccess(@RequestParam("code") String code,@RequestParam("email")String email)
    {
        Map<String,Boolean> response=new HashMap<>();
        if(emailService.findVerificationSignUpCode(email).equals(code))
        {
            response.put("verified",true);
            emailService.deleteVerificationSignUpCode(email);
        }else
        {
            response.put("verified",false);
        }

        return ResponseEntity.ok().body(response);
    }

    /**
     * 임시비빌번호 전송
     */
    @PostMapping("/find/password")
    public ResponseEntity sendEmailTempPassword(@RequestBody EmailRequest emailRequest) throws Exception {

        System.out.println("이메일 보낼꺼얌 " + emailRequest.getEmail());
        String email = emailRequest.getEmail();
        String confirm = emailService.sendSimpleMessageTempPassword(email);

        return ResponseEntity.ok().body("confirm");
    }

}
