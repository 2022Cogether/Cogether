package com.cogether.api.user.web;

import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.dto.UserSkillResponse;
import com.cogether.api.user.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class UserSkillController {

    private final SkillService skillService;

    @PostMapping(value = "/skills", headers = "ACCESS_TOKEN")
    public ResponseEntity addUserSkills(@RequestBody UserRequest userRequest, @RequestHeader("ACCESS_TOKEN") String token) throws Exception {

        return ResponseEntity.ok().body(skillService.addUserSkill(userRequest.getSkills(), token));
    }


    /**
     * 스킬리스트 (List<UserSkillResponse>)
     */
    @GetMapping("/skills/list/{userid}")
    public ResponseEntity getUserSkilllist(@PathVariable("userid") int userId) throws Exception {

        List<UserSkillResponse> list = skillService.getUserSkillList(userId);

        return ResponseEntity.ok().body(list);
    }

    /**
     * 스킬리스트 (List<String>)
     */
    @GetMapping("/skills/{userid}")
    public ResponseEntity getUserSkills(@PathVariable("userid") int userId) throws Exception {

        List<String> list = skillService.getUserSkillListString(userId);

        return ResponseEntity.ok().body(list);
    }

    /**
     * 스킬삭제
     *
     * @param userRequest
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/skills/{userid}", headers = "ACCESS_TOKEN")
    public ResponseEntity removeUserSkill(@RequestBody UserRequest userRequest, @RequestHeader("ACCESS_TOKEN") String token) throws Exception {

        return ResponseEntity.ok().body(skillService.removeUserSkill(userRequest.getSkills(), token));
    }

}
