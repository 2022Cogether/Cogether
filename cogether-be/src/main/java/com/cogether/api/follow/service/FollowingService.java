package com.cogether.api.follow.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.follow.domain.Follow;
import com.cogether.api.follow.repository.FollowRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public int registFollow(int fromId, String token) {

        int id = tokenUtils.getUserIdFromToken(token);

        User toUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        User fromUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (followRepository.findFollowId(toUser.getId(), fromUser.getId()).isEmpty()) {

            followRepository.save(Follow.builder()
                    .user(toUser)
                    .fromId(fromUser.getId())
                    .toEmail(toUser.getEmail())
                    .fromEmail(fromUser.getEmail()).build());
        }


        return 1;
    }

    @Transactional
    public int resignFollow(int fromId, String token) {

        int id = tokenUtils.getUserIdFromToken(token);
        User toUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        User fromUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        System.out.println("삭제");
        System.out.println(followRepository.findFollowId(toUser.getId(), fromUser.getId()));
        followRepository.deleteByIdInQuery(toUser.getId(), fromUser.getId());

        return 1;
    }


    public List<Map<String, String>> loadFollowingList(int fromId) {
        List<Follow> list;
        String email = "";

        //int id = tokenUtils.getUserIdFromToken(token);

        User fromUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //System.out.println("유저 아이디"+id);
        list = followRepository.findByFollowing(fromId);

        List<Map<String, String>> followingList = new ArrayList<>();

        for (Follow follow : list) {
            Map<String, String> following = new HashMap<>();

            following.put("id", Integer.toString(follow.getFromId()));
            following.put("email", follow.getFromEmail());
            User user = userRepository.findByEmail(follow.getFromEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            following.put("nickname",user.getNickname());
            followingList.add(following);
        }

        return followingList;
    }

    public Map<String, Integer> loadFollowingListSize(int fromId) {
        List<Follow> list;
        String email = "";

        //int id = tokenUtils.getUserIdFromToken(token);

        User fromUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //System.out.println("유저 아이디"+id);
        list = followRepository.findByFollowing(fromId);

        int size = list.size();

        Map<String, Integer> body = new HashMap<>();
        body.put("size", size);

        return body;
    }


    public List<Map<String, String>> loadFollowerList(int fromId) {
        List<Follow> list;


        User toUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        list = followRepository.findByFollower(toUser.getId());

        System.out.println("팔로워 수 : " + list.size());

        List<Map<String, String>> followerList = new ArrayList<>();

        for (Follow follow : list) {
            Map<String, String> follower = new HashMap<>();

            follower.put("id", Integer.toString(follow.getUser().getId()));
            follower.put("email", follow.getToEmail());

            User user =userRepository.findByEmail(follow.getToEmail())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            follower.put("nickname",user.getNickname());
            followerList.add(follower);
        }

        return followerList;
    }

    public Map<String, Integer> loadFollowerListSize(int fromId) {
        List<Follow> list;


        User toUser = userRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        list = followRepository.findByFollower(toUser.getId());

        int size = list.size();

        //List<Map<String,String>> followerList = new ArrayList<>();

        Map<String, Integer> body = new HashMap<>();
        body.put("size", size);

        return body;
    }

}
