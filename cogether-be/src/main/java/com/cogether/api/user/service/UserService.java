package com.cogether.api.user.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.user.domain.Auth;
import com.cogether.api.follow.domain.Follow;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.repository.AuthRepository;
import com.cogether.api.follow.repository.FollowRepository;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.api.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 1.로그인
 * 2. 로그아웃
 * 3. 회원가입
 * 4. 이메일 인증
 * 5. 유저정보 조회
 * 6. 닉네임 중복확인
 * 7. 이메일 중복확인
 * 8. 비밀번호 찾기
 * 9. 회원정보수정
 * 10. 유저비밀번호 변경
 * 11. 회원탈퇴
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserSkillRepository userSkillRepository;

    private final FollowRepository followRepository;

    public Optional<User> findByEmail(String userEmail) {

        return userRepository.findByEmail(userEmail);
    }



    public List<UserSkill> findBySkillId(int userId)
    {
        return userSkillRepository.findBySkillId(userId);
    }

    public List<UserSkill> findUser(int userId) {
        List<UserSkill> userSkillList = userSkillRepository.findBySkillId(userId);

        return userSkillList;
    }



    //회원가입
    @Transactional
    public TokenResponse signUp(UserRequest userRequest) {

        //user 테이블 레코드 저장
        User user =
                userRepository.save(
                        User.builder()
                                .password(passwordEncoder.encode(userRequest.getPassword()))  // 사용자 비밀번호 암호화해서 DB에 저장
                                .email(userRequest.getEmail())      //사용자 이메일
                                .nickname(userRequest.getNickname())//사용자 닉네임
                                .build());

        int id = userRequest.getId();   // 식별자

//        List<UserSkill> userSkillList = findUser(id);

        //UserSkill table에 레코드 저장
//        for(UserSkill userSkills : userSkillList)
//        {
//            UserSkill userSkill = userSkillRepository.save(
//                    UserSkill.builder()
//                            .skillId(userSkills.getSkillId())
//                            .user(user)
//                            .build());
//        }

        String accessToken = tokenUtils.generateJwtToken(user);
        String refreshToken = tokenUtils.saveRefreshToken(user);

        authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());      //DB 리프레시 토큰 저장

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(id).build();
    }

    //로그인
    @Transactional
    public TokenResponse signIn(UserRequest userRequest) throws Exception {

        User user =
                userRepository
                        .findByEmail(userRequest.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Optional<Auth> authEntity =
                authRepository
                        .findByUserId(user.getId());
                        //.orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = "";
        String refreshToken = "";
        Auth auth=new Auth();

        if(authEntity.isPresent())
        {
            auth=authEntity.get();
            refreshToken =auth.getRefreshToken();

            //리프레시토큰 검증
            if (tokenUtils.isValidRefreshToken(refreshToken)) {
                accessToken = tokenUtils.generateJwtToken(auth.getUser());
                refreshToken = tokenUtils.saveRefreshToken(user);
                return TokenResponse.builder()
                        .ACCESS_TOKEN(accessToken)
                        .REFRESH_TOKEN(refreshToken)
                        .userId(user.getId())
                        .build();
            }
            else {
                accessToken = tokenUtils.generateJwtToken(auth.getUser());
                refreshToken = tokenUtils.saveRefreshToken(user);
                auth.refreshUpdate(refreshToken);
            }

            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        }
        else
        {
            accessToken = tokenUtils.generateJwtToken(user);
            refreshToken = tokenUtils.saveRefreshToken(user);
            authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());
            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        }

    }
    //로그아웃
    @Transactional
    public void signOut(UserRequest userRequest) throws Exception {
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Auth auth = authRepository.findByUserId(user.getId()).
                orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));

        authRepository.delete(auth);
    }


    public boolean verifyDuplicationOfEmail(UserRequest userRequest)
    {
        Optional<User> user =
                userRepository.findByEmail(userRequest.getEmail());

        if(user.isPresent())
            return true;        // 중복확인
        else
            return false;       // 중복없음
    }


    public boolean verifyDuplicationOfNickName(String nickname)
    {
        Optional<User> user =
                userRepository.findByNickname(nickname);

        if(user.isPresent())
            return true;        // 중복확인
        else
            return false;       // 중복없음
    }

    //회원 정보 수정
    @Transactional
    public TokenResponse modifyUserInfo(UserRequest userRequest)
    {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());


        Optional<Auth> auth = authRepository.findByUserId(user.get().getId());

        String accessToken = "";
        String refreshToken = auth.get().getRefreshToken();

        if (tokenUtils.isValidRefreshToken(refreshToken)) {
            accessToken = tokenUtils.generateJwtToken(auth.get().getUser());
            return TokenResponse.builder()
                    .ACCESS_TOKEN(accessToken)
                    .REFRESH_TOKEN(auth.get().getRefreshToken())
                    .build();
        } else {
            accessToken = tokenUtils.generateJwtToken(auth.get().getUser());
            refreshToken = tokenUtils.saveRefreshToken(user.get());
            auth.get().refreshUpdate(refreshToken);
        }


        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }

    //회원조회
    public User findUserInfo(int user_id) throws Exception
    {
        User user = userRepository
                .findById(user_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return  user;
    }


}
