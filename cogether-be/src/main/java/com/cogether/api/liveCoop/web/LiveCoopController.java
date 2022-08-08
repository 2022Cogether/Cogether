package com.cogether.api.liveCoop.web;

import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.service.LiveCoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCoopController {

    private final LiveCoopService liveCoopService;

    @PostMapping("/livecoop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopId> createLiveCoop(@RequestBody LiveCoopRequest.CreateLiveCoop request) {
        LiveCoopResponse.OnlyLiveCoopId response = liveCoopService.createLiveCoop(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/livecoop/{liveCoopId}")
    public ResponseEntity<LiveCoopResponse.GetLiveCoop> getLiveCoop(@PathVariable int liveCoopId) {
        LiveCoopResponse.GetLiveCoop response = liveCoopService.getLiveCoop(liveCoopId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/livecoop/list/{userId}")
    public ResponseEntity<LiveCoopResponse.GetLiveCoops> getLiveCoops(@PathVariable int userId) {
        LiveCoopResponse.GetLiveCoops response = liveCoopService.getLiveCoops(userId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/livecoop/{liveCoopId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopId> deleteLiveCoop(@PathVariable int liveCoopId) {
        LiveCoopResponse.OnlyLiveCoopId response = liveCoopService.deleteLiveCoop(liveCoopId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/livecoop/member")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopMemberId> createLiveCoopMember(@RequestBody LiveCoopRequest.CreateLiveCoopMember request) {
        LiveCoopResponse.OnlyLiveCoopMemberId response = liveCoopService.createLiveCoopMember(request);
        return ResponseEntity.ok().body(response);
    }

}
