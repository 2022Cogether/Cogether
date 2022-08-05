package com.cogether.api.user;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.user.domain.Auth;
import com.cogether.api.user.domain.Follow;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.repository.AuthRepository;
import com.cogether.api.user.repository.FollowRepository;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.api.user.repository.UserSkillRepository;
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
    public List<Follow> findByFollowList(int userId)
    {
        List<Follow> followingList = followRepository.findById(userId);

        return followingList;
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

        List<UserSkill> userSkillList = findUser(id);

        //UserSkill table에 레코드 저장
        for(UserSkill userSkills : userSkillList)
        {
            UserSkill userSkill = userSkillRepository.save(
                    UserSkill.builder()
                            .skillId(userSkills.getSkillId())
                            .user(user)
                            .build());
        }

        String accessToken = tokenUtils.generateJwtToken(user);
        String refreshToken = tokenUtils.saveRefreshToken(user);

        authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());      //DB 리프레시 토큰 저장

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }

    //로그인
    @Transactional
    public TokenResponse signIn(UserRequest userRequest) throws Exception {
        User user =
                userRepository
                        .findByEmail(userRequest.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth authEntity =
                authRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
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

    public boolean verifyDuplicationOfEmail(UserRequest userRequest)
    {
        Optional<User> user =
                userRepository.findByEmail(userRequest.getEmail());

        if(user.isPresent())
            return true;        // 중복확인
        else
            return false;       // 중복없음
    }

    public boolean verifyDuplicationOfEmail(String email)
    {
        Optional<User> user =
                userRepository.findByEmail(email);

        if(user.isPresent())
            return true;        // 중복확인
        else
            return false;       // 중복없음
    }

    public boolean verifyDuplicationOfNickName(String nickName)
    {
        Optional<User> user =
                userRepository.findByEmail(nickName);

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

    // 사용자가 팔로잉한 팔로워들 리스트 로드
    public int loadFollowingList(UserRequest userRequest){

        Optional<User> user = userRepository.findByUserId(userRequest.getId()); //userRequest에 대한 식별자 알아오기

        return -1;
    }



    //팔로잉 등록
    @Transactional
    public int registFollowing(UserRequest userRequest){

        Optional<User> user = userRepository.findByUserId(userRequest.getId());

        User users = user.get();

       Follow follow=
               followRepository.save(Follow.builder()
                               .toId(users.getId())
                               .user(users)
                       .build());


        return 1;
    }

    //팔로잉 해제
    @Transactional
    public int cancleFollowing(UserRequest userRequest)
    {
        Optional<User> user = userRepository.findByUserId(userRequest.getId());

        User users = user.get();


        followRepository.delete(Follow.builder()
                        .toId(users.getId())
                        .user(users)
                        .build());

        return -1;
    }

    // 사용자를 팔로잉한 팔로워들 리스트 로드
    public int loadFollowerList(UserRequest userRequest){
        return -1;
    }



}
