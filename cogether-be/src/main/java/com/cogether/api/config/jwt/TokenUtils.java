package com.cogether.api.config.jwt;

import com.cogether.api.user.domain.User;
import com.cogether.api.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TokenUtils {

    private final String SECRET_KEY = "secretKey";
    private final String REFRESH_KEY = "refreshKey";
    private final String DATA_KEY = "userID";

    private final UserRepository userRepository;

    //AccessToken 발급
    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())  // 토큰 용도??v JWT payload 에 저장되는 정보단위
                .setHeader(createHeader())  //header 설정
                .setClaims(createClaims(user)) //claims 설정
                .setExpiration(createExpireDate(1000 * 60 * 60 *2)) // 토큰 만료시간 2hour
                .signWith(SignatureAlgorithm.HS256, createSigningKey(SECRET_KEY)) //HS256 , key로 sign
                .compact(); // 토큰 생성
    }

    //RefreshToken 발급
    public String saveRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeaderRefresh())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDate(1000 * 60 * 60*24*7 )) // 토큰 만료시간 7일
                .signWith(SignatureAlgorithm.HS256, createSigningKey(REFRESH_KEY))
                .compact();
    }

    public boolean isValidToken(String token) {
        System.out.println("isValidToken is : " + token);
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("userEmail"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
    public boolean isValidRefreshToken(String token) {
        try {
            Claims accessClaims = getClaimsToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("userEmail"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());


            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }


    private Date createExpireDate(long expireDate) {
        long curTime = System.currentTimeMillis();
        return new Date(curTime + expireDate);
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "ACCESS_TOKEN");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createHeaderRefresh() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "REFRESH_TOKEN");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(DATA_KEY, user.getEmail());
        return claims;
    }

    private Key createSigningKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims getClaimsFormToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }
    private Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public int getUserIdFromToken(String token)
    {


        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims.toString());

        String userEmail =claims.get("userID",String.class);
        System.out.println("토큰에서 뽑아오익 :" + userEmail);

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        int userId = user.getId();

        return userId;
    }
    public int getUserIdFromRefreshToken(String token)
    {

        System.out.println("토큰 뽑아오기");

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims.toString());

        String userEmail =claims.get("userID",String.class);
        System.out.println("토큰에서 뽑아오익 :" + userEmail);

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        int userId = user.getId();

        return userId;
    }
}