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

    @PostMapping(path = "/til/like")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<TilResponse.OnlyLikeId> createLike(@RequestBody TilRequest.Create_TilLike tilLike){
        TilResponse.OnlyLikeId response = tilService.createLike(tilLike);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/til/like/{tilLikeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyLikeId> deleteLike(@PathVariable int tilLikeId){
        TilResponse.OnlyLikeId response = tilService.deleteLike(tilLikeId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/til/comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<TilResponse.OnlyCommentId> createComment(@RequestBody TilRequest.Create_TilComment tilComment){
        TilResponse.OnlyCommentId response = tilService.createComment(tilComment);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(path = "/til/comment")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyCommentId> updateComment(@RequestBody TilRequest.Update_TilComment tilComment){
        TilResponse.OnlyCommentId response = tilService.updateComment(tilComment);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/til/comment/{tilCommentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyCommentId> deleteComment(@PathVariable int tilCommentId){
        TilResponse.OnlyCommentId response = tilService.deleteComment(tilCommentId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/til")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilAll> getTilDetail(@RequestParam int tilId, @RequestParam int userId){
        TilResponse.TilAll response = tilService.getTilDetail(tilId, userId);
        return ResponseEntity.ok().body(response);
    }

}