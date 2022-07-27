package com.cogether.api.til.service;

import com.cogether.api.til.domain.*;
import com.cogether.api.til.exception.TilCommentNotFoundException;
import com.cogether.api.til.exception.TilNotFoundException;
import com.cogether.api.til.repository.TilCommentRepository;
import com.cogether.api.til.repository.TilImgRepository;
import com.cogether.api.til.repository.TilLikeRepository;
import com.cogether.api.til.repository.TilRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;
    private final TilImgRepository tilImgRepository;
    private final TilLikeRepository tilLikeRepository;
    private final TilCommentRepository tilCommentRepository;
    private final UserRepository userRepository;
    public TilResponse.OnlyId create(TilRequest.Create_Til create_til, TilRequest.Create_TilImg create_img){
        User user = userRepository.findById(create_til.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = create_til.toEntity(user);
        Til savedTil = tilRepository.save(til);
        for (int i = 0; i < create_img.getImgList().size(); i++){
            //TODO: 이미지 url 변환 작업
            String img_url = "";
            TilImg tilimg = TilImg.toEntity(savedTil, img_url);
            TilImg savedTilImg = tilImgRepository.save(tilimg);
        }
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId update(TilRequest.Update_Til update_til){
        Til til = tilRepository.findById(update_til.getTilId()).orElseThrow(TilNotFoundException::new);
        til.setTitle(update_til.getTitle());
        til.setContent(update_til.getContent());
        Til savedTil = tilRepository.save(til);
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId delete(int tilId){
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        tilRepository.deleteById(tilId);
        return TilResponse.OnlyId.build(til);
    }

    public TilResponse.OnlyLikeId createLike(TilRequest.Create_TilLike til_Like){
        User user = userRepository.findById(til_Like.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = tilRepository.findById(til_Like.getTilId()).orElseThrow(TilNotFoundException::new);
        TilLike tilLike = til_Like.toEntity(til, user);
        TilLike savedTilLike = tilLikeRepository.save(tilLike);
        return TilResponse.OnlyLikeId.build(savedTilLike);
    }

    public TilResponse.OnlyLikeId deleteLike(int tilLikeId){
        //TODO: Throw안에 들어갈거 상의
        TilLike tilLike = tilLikeRepository.findById(tilLikeId).orElseThrow();
        tilLikeRepository.deleteById(tilLikeId);
        return TilResponse.OnlyLikeId.build(tilLike);
    }

    public TilResponse.OnlyCommentId createComment(TilRequest.Create_TilComment til_Comment){
        User user = userRepository.findById(til_Comment.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = tilRepository.findById(til_Comment.getTilId()).orElseThrow(TilNotFoundException::new);
        TilComment tilComment = til_Comment.toEntity(til, user);
        TilComment savedTilComment = tilCommentRepository.save(tilComment);
        return TilResponse.OnlyCommentId.build(savedTilComment);
    }

    public TilResponse.OnlyCommentId updateComment(TilRequest.Update_TilComment til_Comment){
        TilComment tilComment = tilCommentRepository.findById(til_Comment.getTilCommentId()).orElseThrow(TilCommentNotFoundException::new);
        tilComment.setContent(til_Comment.getContent());
        TilComment savedTilComment = tilCommentRepository.save(tilComment);
        return TilResponse.OnlyCommentId.build(savedTilComment);
    }

    public TilResponse.OnlyCommentId deleteComment(int tilCommentId){
        TilComment tilComment = tilCommentRepository.findById(tilCommentId).orElseThrow(TilCommentNotFoundException::new);
        tilCommentRepository.deleteById(tilCommentId);
        return TilResponse.OnlyCommentId.build(tilComment);
    }

}
