package com.cogether.api.liveComp.web;

import com.cogether.api.liveComp.domain.LiveCompRequest;
import com.cogether.api.liveComp.domain.LiveCompResponse;
import com.cogether.api.liveComp.service.LiveCompService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCompController {

    private final LiveCompService liveCompService;

    @PatchMapping("/liveComp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCompResponse.OnlyId> update(@RequestBody LiveCompRequest.Update request) {
        LiveCompResponse.OnlyId response = liveCompService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/liveComp", headers = "ACCESS_TOKEN")
    public ResponseEntity<LiveCompResponse.GetLiveComp> getLiveComp(@RequestHeader("ACCESS_TOKEN") String token) {
        LiveCompResponse.GetLiveComp response = liveCompService.getLiveComp(token);
        return ResponseEntity.ok().body(response);
    }

}
