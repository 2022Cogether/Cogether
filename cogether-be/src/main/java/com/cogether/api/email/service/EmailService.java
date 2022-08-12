package com.cogether.api.email.service;

import com.cogether.api.email.domain.Verification;
import com.cogether.api.email.dto.Email;
import com.cogether.api.email.repository.EmailRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    @Autowired
   JavaMailSender emailSender;

    public static final String ePw = createKey();
    public static final String tempPassword=createTempPassword();
    private final PasswordEncoder passwordEncoder;
    /**
     * 회원가입 인증메일 보내기 ^&^
     */
    private MimeMessage createMessageSignUp(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("COGETHER - 회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> COGETHER </h1>";
        msgg+= "<br>";
        msgg+= "<p>안녕하세요, COGETHER에 가입해주셔서 감사합니다 !<p>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("cogether.service@gmail.com","COGETHER.Support"));//보내는 사람

        return message;
    }

    private MimeMessage createMessageIssuetemporarypassword
            (String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("COGETHER - 임시비밀번호 발급");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> COGETHER </h1>";
        msgg+= "<br>";
        msgg+= "<p>안녕하세요,"+to+"님, 임시비밀번호 발급드립니다 <p>";
        msgg+= "<p>아래 임시비밀번호로 재로그인하여 새로운 비밀번호로 변경해주세요.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시비밀번호입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= tempPassword+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("cogether.service@gmail.com","COGETHER.Support"));//보내는 사람

        return message;
    }

    /**
     * 회원가입 랜덤 인증코드 만들기 ~
     */
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    /**
     * 임시비밀번호 생성
     */
    public static String createTempPassword()
    {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<10; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return  sb.toString();
    }


    /**
     * 회원가입 인증메일 보내기 ^_^
     */
    @Transactional
    public String sendSimpleMessage(String to)throws Exception {
        System.out.println(to);
        MimeMessage message = createMessageSignUp(to);
        try{//예외처리
            emailSender.send(message);
            emailRepository.save(Verification.builder().email(to).code(ePw).build());
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }
    /**
     * 임시비밀번호 보내기
     */
    @Transactional
    public String sendSimpleMessageTempPassword(String to)throws Exception {
        System.out.println(to);
        MimeMessage message = createMessageIssuetemporarypassword(to);

        User user = userRepository
                .findByEmail(to).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String encodingPassword = passwordEncoder.encode(tempPassword);


        try{//예외처리
            emailSender.send(message);
            user.setPassword(encodingPassword);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    public String findVerificationSignUpCode (String email)
    {
        Verification verification = emailRepository.searchByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이메일입니다"));
        System.out.println("");
        String code = verification.getCode();

        return code;
    }

    @Transactional
    public String deleteVerificationSignUpCode (String email)
    {
        Verification verification = emailRepository.searchByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이메일입니다"));

        emailRepository.deleteByEmail(verification.getEmail());

        return "fuck you";
    }

}
