package com.cogether.api.liveCoop.web;

import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.service.LiveCoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class LiveCoopController {

    private final LiveCoopService liveCoopService;

    @PostMapping("/LiveCoop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LiveCoopResponse.OnlyId> create(@RequestBody LiveCoopRequest.Create request) {
        LiveCoopResponse.OnlyId response = liveCoopService.create(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/LiveCoop/{LiveCoopId}")
    public ResponseEntity<LiveCoopResponse.GetLiveCoop> getPost(@PathVariable int LiveCoopId) {
        LiveCoopResponse.GetLiveCoop response = liveCoopService.getLiveCoop(LiveCoopId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/LiveCoop")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCoopResponse.OnlyId> update(@RequestBody LiveCoopRequest.Update request) {
        LiveCoopResponse.OnlyId response = liveCoopService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/LiveCoop/{LiveCoopId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LiveCoopResponse.OnlyId> delete(@PathVariable int LiveCoopId) {
        LiveCoopResponse.OnlyId response = liveCoopService.delete(LiveCoopId);
        return ResponseEntity.ok().body(response);
    }

}
