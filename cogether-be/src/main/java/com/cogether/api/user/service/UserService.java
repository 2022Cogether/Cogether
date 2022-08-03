package com.cogether.api.user.service;

import com.cogether.api.user.domain.Auth;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.repository.AuthRepository;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.config.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUserId(String userId) {

        return userRepository.findByUserId(userId);
    }

    @Transactional
    public TokenResponse signUp(UserRequest userRequest) {
        User user =
                userRepository.save(
                        User.builder()
                                .password(passwordEncoder.encode(userRequest.getUserPassword()))  // 사용자 비밀번호 암호화해서 DB에 저장
                                .email(userRequest.getUserEmail())
                                .build());

        String accessToken = tokenUtils.generateJwtToken(user);
        String refreshToken = tokenUtils.saveRefreshToken(user);

        authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }

    //로그인
    @Transactional
    public TokenResponse signIn(UserRequest userRequest) throws Exception {
        User user =
                userRepository
                        .findByUserId(userRequest.getUserEmail())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth authEntity =
                authRepository
                        .findByUser(user.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
        if (!passwordEncoder.matches(userRequest.getUserPassword(), user.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = "";
        String refreshToken = authEntity.getRefreshToken();

        System.out.println(refreshToken);
        if (tokenUtils.isValidRefreshToken(refreshToken)) {
            accessToken = tokenUtils.generateJwtToken(authEntity.getUser());
            return TokenResponse.builder()
                    .ACCESS_TOKEN(accessToken)
                    .REFRESH_TOKEN(authEntity.getRefreshToken())
                    .build();
        } else {
            accessToken = tokenUtils.generateJwtToken(authEntity.getUser());
            refreshToken = tokenUtils.saveRefreshToken(user);
            authEntity.refreshUpdate(refreshToken);
        }

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
