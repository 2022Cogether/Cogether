package com.cogether.api.hunting.web;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.hunting.domain.HuntingResponse;
import com.cogether.api.hunting.service.HuntingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class HuntingController {

    private final HuntingService huntingService;

    private final TokenUtils tokenUtils;

    @PostMapping("/hunting")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HuntingResponse.OnlyHuntingId> create(@RequestBody HuntingRequest.CreateHunting request) {
        HuntingResponse.OnlyHuntingId response = huntingService.create(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/hunting/{huntingId}", headers = "ACCESS_TOKEN")
    public ResponseEntity<HuntingResponse.GetHunting> getHunting(@PathVariable int huntingId, @RequestHeader("ACCESS_TOKEN") String token) {
        HuntingResponse.GetHunting response = huntingService.getHunting(huntingId, tokenUtils.getUserIdFromToken(token));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/hunting/list", headers = "ACCESS_TOKEN")
    public ResponseEntity<HuntingResponse.GetHuntings> getHuntingList(@RequestHeader("ACCESS_TOKEN") String token) {
        HuntingResponse.GetHuntings response = huntingService.getHuntingList(token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/hunting/list/my", headers = "ACCESS_TOKEN")
    public ResponseEntity<HuntingResponse.GetHuntings> getMyHuntingList(@RequestHeader("ACCESS_TOKEN") String token) {
        HuntingResponse.GetHuntings response = huntingService.getMyHuntingList(token);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/hunting/{huntingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HuntingResponse.OnlyHuntingId> delete(@PathVariable int huntingId) {
        HuntingResponse.OnlyHuntingId response = huntingService.delete(huntingId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/hunting/scrap")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HuntingResponse.OnlyHuntingScrapId> createHuntingScrap(@RequestBody HuntingRequest.CreateHuntingScrap request) {
        HuntingResponse.OnlyHuntingScrapId response = huntingService.createScrap(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/hunting/scrap/{huntingScrapId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HuntingResponse.OnlyHuntingScrapId> deleteScrap(@PathVariable int huntingScrapId) {
        HuntingResponse.OnlyHuntingScrapId response = huntingService.deleteScrap(huntingScrapId);
        return ResponseEntity.ok().body(response);
    }

}
