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

    @PostMapping("/skills")
    public ResponseEntity addUserSkills(@RequestBody UserRequest userRequest) throws Exception
    {
        System.out.println("진입");
        System.out.println(userRequest.getEmail());
        User user =skillService.getUser(userRequest.getEmail());

        System.out.println(user.getEmail());

        skillService.addUserSkill(userRequest.getSkills(), user.getId());

        return ResponseEntity.ok(HttpStatus.OK);
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
    @DeleteMapping("/skills/{userid}")
    public ResponseEntity removeUserSkill( @RequestBody UserRequest userRequest )  throws  Exception
    {
        User user = skillService.getUser(userRequest.getEmail());
        System.out.println(user.getEmail());
        skillService.removeUserSkill(userRequest.getSkills(),user.getId());

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
