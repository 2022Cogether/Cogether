package com.cogether.api.hunting.web;

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

    @PostMapping("/hunting")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HuntingResponse.OnlyHuntingId> create(@RequestBody HuntingRequest.CreateHunting request) {
        HuntingResponse.OnlyHuntingId response = huntingService.create(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/hunting")
    public ResponseEntity<HuntingResponse.GetHunting> getHunting(@RequestParam int userId, @RequestParam int huntingId) {
        HuntingResponse.GetHunting response = huntingService.getHunting(userId, huntingId);
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
