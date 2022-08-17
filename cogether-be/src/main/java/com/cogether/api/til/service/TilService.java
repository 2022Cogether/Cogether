package com.cogether.api.til.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.file.service.FileUploadService;
import com.cogether.api.follow.domain.Follow;
import com.cogether.api.follow.repository.FollowRepository;
import com.cogether.api.rank.domain.Ranking;
import com.cogether.api.rank.respository.RankingRepository;
import com.cogether.api.til.domain.*;
import com.cogether.api.til.exception.TilCommentNotFoundException;
import com.cogether.api.til.exception.TilNotFoundException;
import com.cogether.api.til.repository.TilCommentRepository;
import com.cogether.api.til.repository.TilImgRepository;
import com.cogether.api.til.repository.TilLikeRepository;
import com.cogether.api.til.repository.TilRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private final FollowRepository followRepository;
    private final RankingRepository rankingRepository;
    private final TokenUtils tokenUtils;

    public TilResponse.OnlyId create(TilRequest.Create_Til create_til, List<MultipartFile> multipartFiles) {
        User user = userRepository.findById(create_til.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = create_til.toEntity(user);
        Til savedTil = tilRepository.save(til);
        if (multipartFiles.size() != 0) {
            for (int i = 0; i < multipartFiles.size(); i++) {
                MultipartFile file = multipartFiles.get(i);
                String img_url = fileUploadService.uploadImage(file);
                TilImg tilimg = TilImg.toEntity(savedTil, img_url);
                tilImgRepository.save(tilimg);
            }
        }
        Ranking ranking = rankingRepository.findByUser(user);
        ranking.setTilCnt(ranking.getTilCnt() + 1);
        rankingRepository.save(ranking);
        user.setExp(user.getExp() + 10);
        userRepository.save(user);
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId update(TilRequest.Update_Til update_til) {
        Til til = tilRepository.findById(update_til.getTilId()).orElseThrow(TilNotFoundException::new);
        til.setTitle(update_til.getTitle());
        til.setContent(update_til.getContent());
        Til savedTil = tilRepository.save(til);
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId delete(int tilId) {
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        List<TilComment> tilCommentList = tilCommentRepository.findAllByTil(til);
        List<TilImg> tilImgList = tilImgRepository.findAllByTil(til);
        List<TilLike> tilLikeList = tilLikeRepository.findAllByTil(til);
        for (int i = 0; i < tilCommentList.size(); i++) {
            tilCommentRepository.deleteById(tilCommentList.get(i).getId());
        }
        for (int i = 0; i < tilImgList.size(); i++) {
            tilImgRepository.deleteById(tilImgList.get(i).getId());
        }
        for (int i = 0; i < tilLikeList.size(); i++) {
            tilLikeRepository.deleteById(tilLikeList.get(i).getId());
        }
        tilRepository.deleteById(tilId);
        Ranking ranking = rankingRepository.findByUser(til.getUser());
        ranking.setTilCnt(ranking.getTilCnt() - 1);
        rankingRepository.save(ranking);
        return TilResponse.OnlyId.build(til);
    }

    public TilResponse.OnlyLikeId createLike(TilRequest.Create_TilLike til_Like) {
        User user = userRepository.findById(til_Like.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = tilRepository.findById(til_Like.getTilId()).orElseThrow(TilNotFoundException::new);
        TilLike tilLike = til_Like.toEntity(til, user);
        TilLike savedTilLike = tilLikeRepository.save(tilLike);
        return TilResponse.OnlyLikeId.build(savedTilLike);
    }

    public TilResponse.OnlyLikeId deleteLike(int tilId, String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        TilLike tilLike = tilLikeRepository.findByTilAndUser(til, user);
        tilLikeRepository.deleteById(tilLike.getId());
        return TilResponse.OnlyLikeId.build(tilLike);
    }

    public TilResponse.OnlyCommentId createComment(TilRequest.Create_TilComment til_Comment) {
        User user = userRepository.findById(til_Comment.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = tilRepository.findById(til_Comment.getTilId()).orElseThrow(TilNotFoundException::new);
        TilComment tilComment = til_Comment.toEntity(til, user);
        TilComment savedTilComment = tilCommentRepository.save(tilComment);
        user.setExp(user.getExp() + 2);
        userRepository.save(user);
        return TilResponse.OnlyCommentId.build(savedTilComment);
    }

    public TilResponse.OnlyCommentId updateComment(TilRequest.Update_TilComment til_Comment) {
        TilComment tilComment = tilCommentRepository.findById(til_Comment.getTilCommentId()).orElseThrow(TilCommentNotFoundException::new);
        tilComment.setContent(til_Comment.getContent());
        TilComment savedTilComment = tilCommentRepository.save(tilComment);
        return TilResponse.OnlyCommentId.build(savedTilComment);
    }

    public TilResponse.OnlyCommentId deleteComment(int tilCommentId) {
        TilComment tilComment = tilCommentRepository.findById(tilCommentId).orElseThrow(TilCommentNotFoundException::new);
        tilCommentRepository.deleteById(tilCommentId);
        return TilResponse.OnlyCommentId.build(tilComment);
    }

    public TilResponse.TilAll getTilDetail(int tilId, String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<TilImg> imgList = tilImgRepository.findAllByTil(til);
        List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
        int likeCnt = tilLikeRepository.countAllByTil(til);
        int check = tilLikeRepository.countAllByTilAndUser(til, user);
        boolean isLike = false;
        if (check == 1) {
            isLike = true;
        }
        return TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike);
    }

    public TilResponse.TilList getSearchTil(String keyword, String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Til> list = tilRepository.findAllByContentContainingIgnoreCaseOrTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrderByCreatedAtDesc(keyword, keyword, keyword);
        for (int i = 0; i < list.size(); i++) {
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if (check == 1) {
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }

    public TilResponse.TilList getMyTilList(String token, int tilUserId) {
        int userId = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User tilUser = userRepository.findById(tilUserId).orElseThrow(UserNotFoundException::new);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        List<Til> list = tilRepository.findAllByUserOrderByCreatedAtDesc(tilUser);
        for (int i = 0; i < list.size(); i++) {
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if (check == 1) {
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }

    public TilResponse.TilList getMySearchTil(String keyword, int tilUserId, String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User tilUser = userRepository.findById(tilUserId).orElseThrow(UserNotFoundException::new);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        List<Til> list = tilRepository.findAllByTitleContainingIgnoreCaseAndUserOrderByCreatedAtDesc(keyword, tilUser);
        for (int i = 0; i < list.size(); i++) {
            Til til = list.get(i);
            List<TilImg> imgList = tilImgRepository.findAllByTil(til);
            List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
            int likeCnt = tilLikeRepository.countAllByTil(til);
            int check = tilLikeRepository.countAllByTilAndUser(til, user);
            boolean isLike = false;
            if (check == 1) {
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
        }
        return TilResponse.TilList.build(tilList);
    }

    public TilResponse.TilList getMainTil(String token) {
        int userId = tokenUtils.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Follow> userList = followRepository.findByFollowing(userId);
        List<TilResponse.TilAll> tilList = new ArrayList<>();
        List<Til> myList = tilRepository.findAllByUser_Id(userId);
        for (int i = 0; i < myList.size(); i++) {
            Til myTil = myList.get(i);
            List<TilImg> myTilImgList = tilImgRepository.findAllByTil(myTil);
            List<TilComment> myTilCommentList = tilCommentRepository.findAllByTil(myTil);
            int likeCnt = tilLikeRepository.countAllByTil(myTil);
            int check = tilLikeRepository.countAllByTilAndUser(myTil, user);
            boolean isLike = false;
            if (check == 1) {
                isLike = true;
            }
            tilList.add(TilResponse.TilAll.build(myTil, myTilImgList, myTilCommentList, likeCnt, isLike));
        }
        for (int i = 0; i < userList.size(); i++) {
            int userFollowingId = userList.get(i).getFromId();
            List<Til> tilFollowingList = tilRepository.findAllByUser_Id(userFollowingId);
            for (int j = 0; j < tilFollowingList.size(); j++) {
                Til til = tilFollowingList.get(j);
                List<TilImg> imgList = tilImgRepository.findAllByTil(til);
                List<TilComment> commentList = tilCommentRepository.findAllByTil(til);
                int likeCnt = tilLikeRepository.countAllByTil(til);
                int check = tilLikeRepository.countAllByTilAndUser(til, user);
                boolean isLike = false;
                if (check == 1) {
                    isLike = true;
                }
                tilList.add(TilResponse.TilAll.build(til, imgList, commentList, likeCnt, isLike));
            }
        }
        Collections.sort(tilList, new Comparator<TilResponse.TilAll>() {
            @Override
            public int compare(TilResponse.TilAll o1, TilResponse.TilAll o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        return TilResponse.TilList.build(tilList);
    }
}
