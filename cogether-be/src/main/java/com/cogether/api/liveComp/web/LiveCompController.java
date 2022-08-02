package com.cogether.api.liveComp.web;

import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.hunting.domain.HuntingResponse;
import com.cogether.api.liveComp.domain.LiveCompRequest;
import com.cogether.api.liveComp.domain.LiveCompResponse;
import com.cogether.api.liveComp.service.LiveCompService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCompController {

    private final LiveCompService liveCompService;

    @PutMapping("/liveComp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCompResponse.OnlyId> update(@RequestBody LiveCompRequest.Update request) {
        LiveCompResponse.OnlyId response = liveCompService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/liveComp/{userId}")
    public ResponseEntity<LiveCompResponse.GetLiveComp> getLiveComp(@PathVariable int userId) {
        LiveCompResponse.GetLiveComp response = liveCompService.getLiveComp(userId);
        return ResponseEntity.ok().body(response);
    }

}
