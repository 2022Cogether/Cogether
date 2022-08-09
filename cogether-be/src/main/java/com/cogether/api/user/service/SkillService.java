package com.cogether.api.user.service;

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

    /**
     * 유저 스킬 등록
     * @param skills
     * @return
     */
    @Transactional
    public int addUserSkill(List<String> skills, int userId) throws Exception
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        for(String skill : skills) {
            userSkillRepository.save(UserSkill.builder()
                    .user(user)
                    .skillId(skill)
                    .build());
        }
        return 1;
    }

    /**
     * 유저 스킬 조회
     * @param userId
     * @return list
     */
    public List<UserSkillResponse> getUserSkillList(int userId) throws Exception{
        List<UserSkill> userSkills =userSkillRepository.findBySkillId(userId);
        List<UserSkillResponse> list =new ArrayList<>();

        for (UserSkill userSkill : userSkills)
        {
           UserSkillResponse userSkillResponse=
                   UserSkillResponse.builder()
                    .skillId(userSkill.getSkillId())
                    .userId(userSkill.getUser().getId())
                    .skillId(userSkill.getSkillId())
                    .build();

            list.add(userSkillResponse);
        }

        return list;
    }

    /**
     * 유저 스킬 삭제
     * @param skills
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    public int removeUserSkill(List<String> skills,int userId) throws Exception
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        for(String skill : skills) {
            userSkillRepository.deleteUserSkill(skill,userId);
        }

        return 1;
    }


}
