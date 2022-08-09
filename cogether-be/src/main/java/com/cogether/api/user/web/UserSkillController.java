package com.cogether.api.user.web;

import com.cogether.api.user.domain.UserSkill;
import com.cogether.api.user.dto.UserSkillResponse;
import com.cogether.api.user.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api")
public class UserSkillController {

    private final SkillService skillService;

    @GetMapping("/skill/{userid}")
    public ResponseEntity getUserSkilllist(@PathVariable("userid") int userId)throws Exception {


        List<UserSkillResponse> list= skillService.getUserSkillList(userId);


        return ResponseEntity.ok().body(list);
    }


}
