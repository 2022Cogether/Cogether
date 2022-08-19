package com.cogether.api.user.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.file.service.FileUploadService;
import com.cogether.api.rank.domain.Ranking;
import com.cogether.api.rank.respository.RankingRepository;
import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilImg;
import com.cogether.api.til.repository.TilImgRepository;
import com.cogether.api.til.repository.TilRepository;
import com.cogether.api.user.domain.Auth;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.TokenResponse;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.AuthRepository;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.api.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

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

    private final RankingRepository rankingRepository;

    private final FileUploadService fileUploadService;

    private final TilRepository tilRepository;
    private final TilImgRepository tilImgRepository;

    public Optional<User> findByEmail(String userEmail) {

        return userRepository.findByEmail(userEmail);
    }


    /**
     * 회원가입
     */
    @Transactional
    public TokenResponse signUp(UserRequest userRequest) {

        User user = userRepository.save(
                User.builder().password(passwordEncoder.encode(userRequest.getPassword()))  // 사용자 비밀번호 암호화해서 DB에 저장
                .email(userRequest.getEmail())
                .nickname(userRequest.getNickname())
                .createdAt(LocalDateTime.now())
                .resign(false)
                .comp(false)
                .admin(false)
                .etcUrl(userRequest.getEtc_url())
                .gitUrl(userRequest.getGit_url())
                .velogUrl(userRequest.getVelog_url())
                .notionUrl((userRequest.getNotion_url()))
                .exp(0) // 경험치
                .imgUrl(userRequest.getImg_url())   // 프로필
                .intro(userRequest.getIntro())// 한줄소개
                .verified(true)    // 이메일 인증여부
                .imgUrl("https://cogethera801.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.png").build());

        List<String> skills = userRequest.getSkills();

        if (!skills.isEmpty()) {
            for (String skill : skills) {
                userSkillRepository.save(UserSkill.builder().skillId(skill).user(user).build());
            }
        }

        rankingRepository.save(Ranking.builder().user(user).tilCnt(0).week(0).month(0).total(0).build());

        Til til =Til.builder()
                .content("코게더에 오신 것을 환영 합니다")
                .createdAt(LocalDateTime.now())
                .title("코게더에 오신 것을 환영 합니다")
                .user(user)
                .build();

        tilRepository.save(til);

        tilImgRepository.save(TilImg.builder()
                .til(til)
                .url("https://cogethera801.s3.ap-northeast-2.amazonaws.com/ee25a7da-69d2-441b-b3f1-b78983f28458.png")
                .build());



        String accessToken = tokenUtils.generateJwtToken(user);
        String refreshToken = tokenUtils.saveRefreshToken(user);

        authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());      //DB 리프레시 토큰 저장

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenResponse signIn(UserRequest userRequest) throws Exception {

        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Optional<Auth> authEntity = authRepository.findByUserId(user.getId());


        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = "";
        String refreshToken = "";

        Auth auth = new Auth();

        if (authEntity.isPresent()) {
            auth = authEntity.get();
            refreshToken = auth.getRefreshToken();

            //리프레시토큰 검증
            if (tokenUtils.isValidRefreshToken(refreshToken)) {
                accessToken = tokenUtils.generateJwtToken(auth.getUser());
                return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
            } else {
                accessToken = tokenUtils.generateJwtToken(auth.getUser());
                refreshToken = tokenUtils.saveRefreshToken(user);
                auth.refreshUpdate(refreshToken);
            }

            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        } else {
            accessToken = tokenUtils.generateJwtToken(user);
            refreshToken = tokenUtils.saveRefreshToken(user);
            authRepository.save(Auth.builder().user(user).refreshToken(refreshToken).build());
            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        }

    }

    /**
     * 엑세스토큰 재발급
     *
     * @return
     * @throws Exception
     */
    @Transactional
    public TokenResponse reissuanceAccessToken(String token, int id) throws Exception {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth auth = authRepository
                .findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));


        String accessToken;
        String refreshToken = token;


        if (tokenUtils.isValidRefreshToken(token) && token.equals(auth.getRefreshToken())) {
            accessToken = tokenUtils.generateJwtToken(user);
            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        } else {
            accessToken = tokenUtils.generateJwtToken(user);
            refreshToken = tokenUtils.saveRefreshToken(user);

            auth.refreshUpdate(refreshToken);
            authRepository.save(auth);

            return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).userId(user.getId()).build();
        }
    }


    /**
     * 로그아웃
     */
    @Transactional
    public Map<String, Boolean> signOut(String token) throws Exception {

        int userId = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Auth auth = authRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));

        authRepository.delete(auth);

        Map<String, Boolean> body = new HashMap<>();

        body.put("isLogOut", true);

        return body;
    }

    /**
     * 이메일 중복확인
     */
    public boolean verifyDuplicationOfEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        System.out.println(email);
        System.out.println(user.isPresent());

        if (user.isPresent()) return true;        // 중복확인
        else return false;       // 중복없음

    }

    /**
     * 닉네임 중복확인
     */
    public boolean verifyDuplicationOfNickName(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);

        System.out.println(nickname);
        System.out.println(user.isPresent());
        if (user.isPresent()) return true;        // 중복확인
        else return false;       // 중복없음
    }

    /**
     * 회원정보수정
     */
    @Transactional
    public String modifyUserInfo(UserRequest userRequest, String token) {

        int userId = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Optional<UserSkill> userSkill = userSkillRepository.findById(userId);

        user.setEtcUrl(userRequest.getEtc_url());
        user.setNickname(userRequest.getNickname());
        user.setIntro(userRequest.getIntro());
        user.setGitUrl(userRequest.getGit_url());
        user.setTistoryUrl(userRequest.getTistory_url());
        user.setVelogUrl(userRequest.getVelog_url());
        user.setNotionUrl(userRequest.getNotion_url());
        user.setImgUrl(userRequest.getImg_url());

        userRepository.save(user);

        return "회원정보 변경 완료";

    }

    /**
     * 회원조회
     */
    public User findUserInfo(int userId) throws Exception {


        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        return user;
    }

    /**
     * 비밀번호 변경
     */
    public int modifyPassword(UserRequest userRequest) throws Exception {
        int id = userRequest.getId();
        String password = userRequest.getPassword();
        System.out.println(id + " : " + password);
        String encodingPassword = passwordEncoder.encode(password);

        User user = userRepository.findById(userRequest.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.setPassword(encodingPassword);

        userRepository.save(user);

        return 1;
    }

    /**
     * 탈퇴
     */
    public int resignUser(String token) throws Exception {

        int userId = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.setResign(true);

        userRepository.save(user);

        return userId;
    }

    /**
     * 프로필 이미지 서버 업로드
     */

    @Transactional
    public Map<String, Integer> uploadProfileImg(String token, MultipartFile multipartFile) {
        Map<String, Integer> body = new HashMap<>();

        int id = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        if (!multipartFile.isEmpty()) {
            String img_url = fileUploadService.uploadImage(multipartFile);
            user.setImgUrl(img_url);
            userRepository.save(user);
        }

        return body;
    }

    public Map<String, Boolean> verifyPassword(UserRequest userRequest, String token) {

        int id = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String verifiedPassword = passwordEncoder.encode(userRequest.getPassword());

        System.out.println(verifiedPassword);
        System.out.println(user.getPassword());

        if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            Map<String, Boolean> body = new HashMap<>();
            body.put("verified", true);


            return body;
        } else {
            Map<String, Boolean> body = new HashMap<>();
            body.put("verified", false);
            return body;
        }

    }

    public List<Map<String, Object>> findNickName(String word) {
        StringBuilder sb = new StringBuilder();
        sb.append("%").append(word).append("%");

        List<User> nickNameList = userRepository.findAllByNicknameIsLike(sb.toString());

        List<Map<String, Object>> body = new ArrayList<>();

        if (!nickNameList.isEmpty()) {
            for (User u : nickNameList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", u.getId());
                map.put("email", u.getEmail());
                map.put("nickName", u.getNickname());

                body.add(map);
            }
        }

        return body;

    }

    public List<Map<String, Object>> findEmail(String word) {
        StringBuilder sb = new StringBuilder();
        System.out.println();
        sb.append("%").append(word).append("%");

        List<User> emailList = userRepository.findAllByEmailIsLike(sb.toString());

        List<Map<String, Object>> body = new ArrayList<>();

        if (!emailList.isEmpty()) {
            for (User u : emailList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", u.getId());
                map.put("email", u.getEmail());

                body.add(map);
            }
        }

        return body;

    }


}
