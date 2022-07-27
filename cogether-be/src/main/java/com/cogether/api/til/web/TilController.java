package com.cogether.api.til.web;

import com.cogether.api.til.domain.TilRequest;
import com.cogether.api.til.domain.TilResponse;
import com.cogether.api.til.service.TilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class TilController {

    private final TilService tilService;

    @PostMapping(path = "/til")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<TilResponse.OnlyId> create(@RequestBody TilRequest.Create_Til til, @RequestPart(value="image", required=false) TilRequest.Create_TilImg create_img){
        TilResponse.OnlyId response = tilService.create(til, create_img);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(path = "/til")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyId> update(@RequestBody TilRequest.Update_Til til){
        TilResponse.OnlyId response = tilService.update(til);
        return  ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/til/{tilId}")
    @ResponseStatus(value =  HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyId> delete(@PathVariable int tilId){
        TilResponse.OnlyId response = tilService.delete(tilId);
        return ResponseEntity.ok().body(response);
    }

}