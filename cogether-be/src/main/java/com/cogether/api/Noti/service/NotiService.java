package com.cogether.api.Noti.service;

import com.cogether.api.Noti.domain.Noti;
import com.cogether.api.Noti.dto.NotiRequest;
import com.cogether.api.Noti.dto.NotiResponse;
import com.cogether.api.Noti.repository.NotiRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 알림 등록
 * 2. 알림 리스트 조회
 * 3. 알림 읽음 처리
 */


@Service
@RequiredArgsConstructor
public class NotiService {

    private final UserRepository userRepository;

    private final NotiRepository notiRepository;

    /**
     * 1. 알림등록
     * a. 댓글, 좋아요, DM, 팔로우 알림 (comment, like, DM, follow)
     */
    @Transactional
    public NotiResponse registerNoti(NotiRequest notiRequest) {
        User toUser = userRepository.findById(notiRequest.getSendId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LocalDateTime createAt = LocalDateTime.now();

        notiRepository.save(new Noti().builder()
                .user(toUser)
                .receiveId(notiRequest.getRecieveId())
                .type(notiRequest.getType())
                .url(notiRequest.getUrl())
                .contentId(notiRequest.getContentId())
                .createAt(createAt)
                .readAt(null)
                .build());

        NotiResponse notiResponse = NotiResponse.builder()
                .sendId(toUser.getId())
                .receiveId(notiRequest.getRecieveId())
                .type(notiRequest.getType())
                .url(notiRequest.getUrl())
                .contentId(notiRequest.getContentId())
                .createAt(createAt)
                .build();


        return notiResponse;
    }

    /**
     * 2. 해당...유저 알림 가져오기 (읽은것도 몽땅)
     *
     * @param
     * @return
     */
    public List<NotiResponse> getUserNoti(int userId) {
        User toUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LocalDateTime createAt = LocalDateTime.now();

        List<Noti> notis = notiRepository.findAllByReceiveId(toUser.getId());
        List<NotiResponse> list = new ArrayList<>();

        for (Noti noti : notis) {
            NotiResponse notiResponse = NotiResponse.builder()
                    .userId(toUser.getId())
                    .type(noti.getType())
                    .url(noti.getUrl())
                    .contentId(noti.getContentId())
                    .createAt(createAt)
                    .build();

            list.add(notiResponse);
        }


        return list;
    }

    /**
     * 2. 안읽은 알림만 가져오깅
     */
    public List<NotiResponse> getUserNotiNotRead(int userId) {
        User User = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LocalDateTime createAt = LocalDateTime.now();

        List<Noti> notis = notiRepository.findAllByUser(User);
        List<NotiResponse> list = new ArrayList<>();

        for (Noti noti : notis) {
            if (noti.getReadAt() == null) {

                NotiResponse notiResponse = NotiResponse.builder()
                        .receiveId(noti.getReceiveId())
                        .type(noti.getType())
                        .url(noti.getUrl())
                        .contentId(noti.getContentId())
                        .createAt(createAt)
                        .build();

                list.add(notiResponse);
            }
        }


        return list;
    }

    /**
     * 알람 읽기
     */
    @Transactional
    public int readNoti(NotiRequest notiRequest) {
        NotiResponse notiResponse;
        User toUser = userRepository.findById(notiRequest.getSendId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Noti noti = notiRepository.findByContentId(notiRequest.getContentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알람입니다."));

        noti.setReadAt(LocalDateTime.now());

        notiRepository.save(noti);

        return 1;
    }


}
