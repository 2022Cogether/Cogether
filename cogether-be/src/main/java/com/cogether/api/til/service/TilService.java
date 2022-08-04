package com.cogether.api.til.service;

import com.cogether.api.file.service.FileUploadService;
import com.cogether.api.til.domain.*;
import com.cogether.api.til.exception.TilCommentNotFoundException;
import com.cogether.api.til.exception.TilLikeNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;
    private final TilImgRepository tilImgRepository;
    private final TilLikeRepository tilLikeRepository;
    private final TilCommentRepository tilCommentRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    public TilResponse.OnlyId create(TilRequest.Create_Til create_til, TilRequest.Create_TilImg create_img){
        User user = userRepository.findById(create_til.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = create_til.toEntity(user);
        Til savedTil = tilRepository.save(til);
        for (int i = 0; i < create_img.getImgList().size(); i++){
            //TODO: 등록테스트 필요
            String img_url = fileUploadService.uploadImage(create_img.getImgList().get(i));
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
        TilLike tilLike = tilLikeRepository.findById(tilLikeId).orElseThrow(TilLikeNotFoundException::new);
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

    public TilResponse.TilAll getTilDetail(int tilId, int userId){
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<TilImg> imgList = tilImgRepository.findAllByTil(til);
        List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
        int likeCnt = tilLikeRepository.countAllByTil(til);
        int check = tilLikeRepository.countAllByTilAndUser(til, user);
        boolean isLike = false;
        if(check == 1) {
            isLike = true;
        }
        return TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike);
    }

    public TilResponse.TilList getSearchTil(String keyword, int userId){
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Til> list = tilRepository.findAllByContentContainingIgnoreCaseOrTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword, keyword);
        for(int i = 0 ; i < list.size() ; i++){
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if(check == 1){
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }

    public TilResponse.TilList getMyTilList(int userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        List<Til> list = tilRepository.findAllByUserOrderByCreatedAtDesc(user);
        for(int i = 0 ; i < list.size() ; i++){
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if(check == 1){
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }

    public TilResponse.TilList getMySearchtil(String keyword, int userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        List<Til> list = tilRepository.findAllByTitleContainingIgnoreCaseAndUserOrderByCreatedAt(keyword, user);
        for(int i = 0 ; i < list.size() ; i++){
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if(check == 1){
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }
}
