package com.cogether.api.liveCoop.web;

import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.service.LiveCoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(value = "/livecoop/list", headers = "ACCESS_TOKEN")
    public ResponseEntity<LiveCoopResponse.GetLiveCoops> getLiveCoops(@RequestHeader("ACCESS_TOKEN") String token) {
        LiveCoopResponse.GetLiveCoops response = liveCoopService.getLiveCoops(token);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/livecoop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopId> startLiveCoop(@RequestBody LiveCoopRequest.StartLiveCoop request) {
        LiveCoopResponse.OnlyLiveCoopId response = liveCoopService.startLiveCoop(request);
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

    @GetMapping("/livecoop/member/{liveCoopMemberId}")
    public ResponseEntity<LiveCoopResponse.GetLiveCoopMember> getLiveCoopMember(@PathVariable int liveCoopMemberId) {
        LiveCoopResponse.GetLiveCoopMember response = liveCoopService.getLiveCoopMember(liveCoopMemberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/livecoop/member/list/{liveCoopId}")
    public ResponseEntity<LiveCoopResponse.GetLiveCoopMembers> getLiveCoopMembers(@PathVariable int liveCoopId) {
        LiveCoopResponse.GetLiveCoopMembers response = liveCoopService.getLiveCoopMembers(liveCoopId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/livecoop/member")
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopMemberId> getLiveCoopMembers(@RequestBody LiveCoopRequest.UpdateLiveCoopMember request) {
        LiveCoopResponse.OnlyLiveCoopMemberId response = liveCoopService.updateLiveCoopMember(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/livecoop/member/{liveCoopMemberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCoopResponse.OnlyLiveCoopMemberId> deleteLiveCoopMember(@PathVariable int liveCoopMemberId) {
        LiveCoopResponse.OnlyLiveCoopMemberId response = liveCoopService.deleteLiveCoopMember(liveCoopMemberId);
        return ResponseEntity.ok().body(response);
    }

}
