package com.cogether.api.user.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.UserSkillResponse;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.api.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 스킬등록
 * 2. 스킬조회
 * 3. 스킬삭제
 */
@Service
@RequiredArgsConstructor
public class SkillService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    private final TokenUtils tokenUtils;

    public User getUser(int userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return user;
    }

    public User getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return user;
    }


    /**
     * 유저 스킬 등록
     *
     * @param skills
     * @return
     */
    @Transactional
    public String addUserSkill(List<String> skills, String token) throws Exception {
        int id = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        for (String skill : skills) {
            userSkillRepository.save(UserSkill.builder()
                    .user(user)
                    .skillId(skill)
                    .build());
        }
        return "add SkillList";
    }

    /**
     * 유저 스킬 조회
     * //예쁘게 나옴 제이슨
     */
    public List<UserSkillResponse> getUserSkillList(int userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        List<UserSkill> skills = userSkillRepository.findAllByUser(user);

        List<UserSkillResponse> list = new ArrayList<>();

        for (UserSkill skill : skills) {
            UserSkillResponse userSkillResponse = UserSkillResponse.builder().userSkillId(skill.getId()).skillName(skill.getSkillId()).build();
            list.add(userSkillResponse);
        }

        return list;
    }

    /**
     * 유저 스킬 조회
     * //리스트하나에 String 주르르륵
     */
    public List<String> getUserSkillListString(int userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        List<UserSkill> skills = userSkillRepository.findAllByUser(user);

        List<String> list = new ArrayList<>();

        for (UserSkill skill : skills) {

            list.add(skill.getSkillId());
        }

        return list;
    }


    /**
     * 유저 스킬 삭제
     *
     * @return
     * @throws Exception
     */
    @Transactional
    public String removeUserSkill(List<String> skills, String token) throws Exception {
        int id = tokenUtils.getUserIdFromToken(token);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        for (String skill : skills) {
            userSkillRepository.deleteUserSkill(skill, id);
        }

        return "remove skills";
    }


}
