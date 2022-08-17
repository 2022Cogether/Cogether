package com.cogether.api.til.web;

import com.cogether.api.til.domain.TilRequest;
import com.cogether.api.til.domain.TilResponse;
import com.cogether.api.til.service.TilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class TilController {

    private final TilService tilService;

    @PostMapping(path = "/til", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<TilResponse.OnlyId> create(@RequestPart(value = "data") TilRequest.Create_Til til, @RequestPart(value="image", required=false)List<MultipartFile> multipartFiles){
        TilResponse.OnlyId response = tilService.create(til, multipartFiles);
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

    @DeleteMapping(path = "/til/like/{tilId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.OnlyLikeId> deleteLike(@PathVariable int tilId, @RequestHeader("ACCESS_TOKEN") String token){
        TilResponse.OnlyLikeId response = tilService.deleteLike(tilId, token);
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

    @GetMapping(path = "/til/{tilId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilAll> getTilDetail(@PathVariable int tilId, @RequestHeader("ACCESS_TOKEN") String token){
        TilResponse.TilAll response = tilService.getTilDetail(tilId, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/til/search/{keyword}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilList> getSearchTil(@PathVariable String keyword, @RequestHeader("ACCESS_TOKEN") String token){
        TilResponse.TilList response = tilService.getSearchTil(keyword, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/til/list/my/{tilUserId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilList> getMyTilList(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int tilUserId){
        TilResponse.TilList response = tilService.getMyTilList(token, tilUserId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/til/search/my/")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilList> getMySearchTil(@RequestParam String keyword, @RequestParam int tilUserId, @RequestHeader("ACCESS_TOKEN") String token){
        TilResponse.TilList response = tilService.getMySearchTil(keyword, tilUserId, token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/til/list")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TilResponse.TilList> getMainTil(@RequestHeader("ACCESS_TOKEN") String token){
        TilResponse.TilList response = tilService.getMainTil(token);
        return ResponseEntity.ok().body(response);
    }
}