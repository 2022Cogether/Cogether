package com.cogether.api.user.web;

import com.cogether.api.user.domain.User;
import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.UserRequest;
import com.cogether.api.user.dto.UserSkillResponse;
import com.cogether.api.user.service.SkillService;
import com.cogether.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api")
public class UserSkillController {

    private final SkillService skillService;

    @PostMapping(value="/skills",headers = "ACCESS_TOKEN")
    public ResponseEntity addUserSkills(@RequestBody UserRequest userRequest,@RequestHeader("ACCESS_TOKEN")String token) throws Exception
    {
        //System.out.println("진입");
        //System.out.println(userRequest.getEmail());
        //User user =skillService.getUser(userRequest.getEmail());
        //System.out.println(user.getEmail());



        return ResponseEntity.ok().body( skillService.addUserSkill(userRequest.getSkills(), token));
    }


    /**
     * 스킬리스트 (List<UserSkillResponse>)
     */
    @GetMapping("/skills/list/{userid}")
    public ResponseEntity getUserSkilllist(@PathVariable("userid") int userId)throws Exception {

        //User user = skillService.getUser(userId);

        List<UserSkillResponse> list = skillService.getUserSkillList(userId);
        System.out.println(list.size());
        //List<UserSkillResponse> list= skillService.getUserSkillList(userId);


        return ResponseEntity.ok().body(list);
    }
    /**
     * 스킬리스트 (List<String>)
     */
    @GetMapping("/skills/{userid}")
    public ResponseEntity getUserSkills(@PathVariable("userid") int userId)throws Exception {
        //User user = skillService.getUser(userId);

        List<String > list = skillService.getUserSkillListString(userId);
        System.out.println(list.size());
        //List<UserSkillResponse> list= skillService.getUserSkillList(userId);


        return ResponseEntity.ok().body(list);
    }

    /**
     * 스킬삭제
     * @param userRequest
     * @return
     * @throws Exception
     */
    @DeleteMapping(value="/skills/{userid}",headers = "ACCESS_TOKEN")
    public ResponseEntity removeUserSkill( @RequestBody UserRequest userRequest,@RequestHeader("ACCESS_TOKEN")String token )  throws  Exception
    {
        //User user = skillService.getUser(userRequest.getEmail());
        //System.out.println(user.getEmail());
        // skillService.removeUserSkill(userRequest.getSkills(),token);

        return ResponseEntity.ok().body(skillService.removeUserSkill(userRequest.getSkills(),token));
    }

}
