package com.cogether.api.follow.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.follow.domain.Follow;
import com.cogether.api.follow.dto.FollowRequest;
import com.cogether.api.follow.repository.FollowRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 1.팔로우 등록
 * 2.팔로우 해제
 * 3.내 팔로잉 목록 보기
 * 4.내 팔로워 목록 보기
 * 5. 내 팔로잉 수 보기
 * 6. 내 팔로워 수 보기
 */

/**
 * toUser : 나
 * fromUser : 너
 */

@Service
@RequiredArgsConstructor
public class FollowingService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    private final TokenUtils tokenUtils;
    @Transactional
    public int registFollow(FollowRequest followRequest) {

        User toUser = userRepository.findById(followRequest.getToId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        User fromUser = userRepository.findById(followRequest.getFromId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(followRepository.findFollowId(toUser.getId(),fromUser.getId()).isEmpty()){

            followRepository.save(Follow.builder()
                    .user(toUser)
                    .fromId(fromUser.getId())
                    .toEmail(toUser.getEmail())
                    .fromEmail(fromUser.getEmail()).build());
        }


        return 1;
    }

    @Transactional
    public int resignFollow(FollowRequest followRequest) {
        User toUser = userRepository.findById(followRequest.getToId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        User fromUser = userRepository.findById(followRequest.getFromId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        System.out.println("삭제");
        System.out.println(followRepository.findFollowId(toUser.getId(), fromUser.getId()));
        followRepository.deleteByIdInQuery(toUser.getId(), fromUser.getId());

        return 1;
    }


    public List<Map<String, String>> loadFollowingList( String token) {
        List<Follow> list ;
        String email="";

        int id = tokenUtils.getUserIdFromToken(token);

//        User toUser = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        System.out.println("유저 아이디"+id);
        list = followRepository.findByFollowing(id);

        List<Map<String,String>> followingList = new ArrayList<>();

        for(Follow follow : list)
        {
            Map<String,String> following =new HashMap<>();

            following.put("id",Integer.toString(follow.getFromId()));
            following.put("email",follow.getFromEmail());
            followingList.add(following);
        }

        return followingList;
    }

    public List<Map<String, String>> loadFollowerList(String token) {
        List<Follow> list ;

        int id = tokenUtils.getUserIdFromToken(token);

//        User toUser = userRepository.findById(followRequest.getToId())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        list = followRepository.findByFollower(id);

        System.out.println("팔로워 수 : "+list.size());

        List<Map<String,String>> followerList = new ArrayList<>();

        for(Follow follow : list)
        {
            Map<String,String> follower =new HashMap<>();

            follower.put("id",Integer.toString(follow.getUser().getId()));
            follower.put("email",follow.getToEmail());
            followerList.add(follower);
        }

        return followerList;
    }

}
