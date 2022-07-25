package com.cogether.api.hunting.web;

import com.cogether.api.hunting.dto.HuntingRequest;
import com.cogether.api.hunting.dto.HuntingResponse;
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
    public ResponseEntity<HuntingResponse.OnlyId> create(@RequestBody HuntingRequest.Create request) {
        HuntingResponse.OnlyId response = huntingService.create(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/hunting")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HuntingResponse.OnlyId> update(@RequestBody HuntingRequest.Update request) {
        HuntingResponse.OnlyId response = huntingService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/hunting/{huntingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HuntingResponse.OnlyId> delete(@PathVariable int huntingId) {
        HuntingResponse.OnlyId response = huntingService.delete(huntingId);
        return ResponseEntity.ok().body(response);
    }

}
