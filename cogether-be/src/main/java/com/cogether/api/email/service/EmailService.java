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

        String mailForm = "<div style='width:960px; margin:0 auto;'>";
        mailForm += "<table  cellpadding='0' cellspacing='0' border='0' width='960' align='left' valign='middle' style='margin:0 auto; padding:0; min-width:960px; border:1px solid #ebebeb; font-family:'맑은 고딕', 'Malgun Gothic', '돋움', Dotum, sans-serif; font-size:18px; color:#666666; letter-spacing:-1.3px; line-height:1.8;'>";
        mailForm += "<tbody>";
        mailForm += "<tr>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='820' height='80'></td>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30' align='right'>";
        mailForm += "<a href='https://i7a801.p.ssafy.io'><img src='https://cogethera801.s3.ap-northeast-2.amazonaws.com/icon.png' alt='코게더 로고' style='height:72px;'/></a>";
        mailForm += "</td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:40px; color:#333333; font-weight:300; line-height:1; letter-spacing:-4px;'>회원가입 이메일 인증 코드 안내</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:16px;'>안녕하세요.<br/>" + "COGETHER에 가입해주셔서 감사합니다 .<br/>해당 코드를 회원가입 창으로 돌아가 입력해주세요</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td style='padding:30px; background:#f5f5f5; text-align:center;'>";
        mailForm += "<p style='font-size:25px; font-weight:500; color:#333333;'><span style='margin-right:20px; color:#666666;'>가입코드 : </span>" + ePw + "</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:16px;'>감사합니다.</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='70' height='200'></td>";
        mailForm += "</tr>";
        mailForm += "</tbody>";
        mailForm += "</table>";
        mailForm += "<p1><a href='https://i7a801.p.ssafy.io'><img src='https://cogethera801.s3.ap-northeast-2.amazonaws.com/logo.png' alt='코게더 로고' style='height:32px;'/></a><p1>";
        mailForm+= "<div width='100'></div>";

        mailForm +="고객센터 | cogether.service@gmail.com <br>" +"광고문의 | cogether.service@gmail.com <br>";

        mailForm+="제휴 및 대외 협력 | cogether.service@gmail.com<br>";
        mailForm += "대표 | 김진회 고나령 신성은 유지연 최홍철 | 서울특별시 강남구 테헤란로 212 멀티캠퍼스 17층<br>";
        mailForm += "</div>";
        message.setText(mailForm, "UTF-8", "html");

        message.setFrom(new InternetAddress("cogether.service@gmail.com","COGETHER.Support"));//보내는 사람

        return message;
    }

    private MimeMessage createMessageIssuetemporarypassword
            (String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+tempPassword);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("COGETHER - 임시비밀번호 발급");//제목

        String mailForm = "<div style='width:960px; margin:0 auto;'>";
        mailForm += "<table  cellpadding='0' cellspacing='0' border='0' width='960' align='left' valign='middle' style='margin:0 auto; padding:0; min-width:960px; border:1px solid #ebebeb; font-family:'맑은 고딕', 'Malgun Gothic', '돋움', Dotum, sans-serif; font-size:18px; color:#666666; letter-spacing:-1.3px; line-height:1.8;'>";
        mailForm += "<tbody>";
        mailForm += "<tr>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='820' height='80'></td>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30' align='right'>";
        mailForm += "<a href='https://i7a801.p.ssafy.io'><img src='https://cogethera801.s3.ap-northeast-2.amazonaws.com/icon.png' alt='코게더 로고' style='height:72px;'/></a>";
        mailForm += "</td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:40px; color:#333333; font-weight:300; line-height:1; letter-spacing:-4px;'>임시비밀번호 발급 안내</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "<td height='30'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:16px;'>안녕하세요."+to + " 님. 해당 임시 비밀번호로 로그인한 후 비밀번호 변경을 진행해주세요. ";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td style='padding:30px; background:#f5f5f5; text-align:center;'>";
        mailForm += "<p style='font-size:25px; font-weight:500; color:#333333;'><span style='margin-right:20px; color:#666666;'>가입코드 : </span>" + tempPassword + "</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "<td height='15'></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td></td>";
        mailForm += "<td>";
        mailForm += "<p style='font-size:16px;'>감사합니다.</p>";
        mailForm += "</td>";
        mailForm += "<td></td>";
        mailForm += "</tr>";
        mailForm += "<tr>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='70' height='80'></td>";
        mailForm += "<td width='70' height='200'></td>";
        mailForm += "</tr>";
        mailForm += "</tbody>";
        mailForm += "</table>";
        mailForm += "<p1><a href='https://i7a801.p.ssafy.io'><img src='https://cogethera801.s3.ap-northeast-2.amazonaws.com/logo.png' alt='코게더 로고' style='height:32px;'/></a><p1>";
        mailForm+= "<div width='100'></div>";

        mailForm +="고객센터 | cogether.service@gmail.com <br>" +"광고문의 | cogether.service@gmail.com <br>";

        mailForm+="제휴 및 대외 협력 | cogether.service@gmail.com<br>";
        mailForm += "대표 | 김진회 고나령 신성은 유지연 최홍철 | 서울특별시 강남구 테헤란로 212 멀티캠퍼스 17층<br>";
        mailForm += "</div>";
        message.setText(mailForm, "utf-8", "html");//내용
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
        sr.setSeed(System.currentTimeMillis());

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
            userRepository.save(user);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return tempPassword;
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
    public boolean deleteVerificationSignUpCode (String email)
    {
        Verification verification = emailRepository.searchByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이메일입니다"));

        emailRepository.deleteByEmail(verification.getEmail());

        return true;
    }

}
