package com.cogether.api.hunting.service;

import com.cogether.api.config.jwt.TokenUtils;
import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.hunting.domain.HuntingResponse;
import com.cogether.api.hunting.domain.HuntingScrap;
import com.cogether.api.hunting.exception.HuntingNotFoundException;
import com.cogether.api.hunting.repository.HuntingRepository;
import com.cogether.api.hunting.repository.HuntingScrapRepository;
import com.cogether.api.user.domain.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import com.cogether.api.user.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HuntingService {

    private final HuntingRepository huntingRepository;
    private final HuntingScrapRepository huntingScrapRepository;
    private final UserRepository userRepository;

    private final TokenUtils tokenUtils;

    private final SkillService skillService;

    public HuntingResponse.OnlyHuntingId create(HuntingRequest.CreateHunting request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Hunting hunting = request.toEntity(user);
        Hunting savedHunting = huntingRepository.save(hunting);
        user.setExp(user.getExp() + 5);
        userRepository.save(user);
        return HuntingResponse.OnlyHuntingId.build(savedHunting);
    }

    public HuntingResponse.GetHunting getHunting(int huntingId, int userId) {
        Hunting hunting = huntingRepository.findById(huntingId).orElseThrow(HuntingNotFoundException::new);
        User loginUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        int cnt = huntingScrapRepository.countAllByUserAndHunting(loginUser, hunting);

        if (cnt == 0)
            return HuntingResponse.GetHunting.build(hunting, false, 0, skillService.getUserSkillList(hunting.getUser().getId()));

        HuntingScrap huntingScrap = huntingScrapRepository.findByUserAndHunting(loginUser, hunting);
        return HuntingResponse.GetHunting.build(hunting, true, huntingScrap.getId(), skillService.getUserSkillList(userId));
    }

    public HuntingResponse.GetHuntings getHuntingList(String token) {
        List<Hunting> hunting = huntingRepository.findAllByOrderByIdDesc();
        List<HuntingResponse.GetHunting> huntingList = new ArrayList<>();
        int len = hunting.size();
        for (int idx = 0; idx < len; idx++)
            huntingList.add(getHunting(hunting.get(idx).getId(), tokenUtils.getUserIdFromToken(token)));

        return HuntingResponse.GetHuntings.build(huntingList);
    }

    public HuntingResponse.GetHuntings getMyHuntingList(String token) {
        User loginUser = userRepository.findById(tokenUtils.getUserIdFromToken(token)).orElseThrow(UserNotFoundException::new);
        List<Hunting> hunting = huntingRepository.findAllByUserOrderByIdDesc(loginUser);
        List<HuntingResponse.GetHunting> huntingList = new ArrayList<>();
        int len = hunting.size();
        for (int idx = 0; idx < len; idx++)
            huntingList.add(getHunting(hunting.get(idx).getId(), loginUser.getId()));

        return HuntingResponse.GetHuntings.build(huntingList);
    }

    public HuntingResponse.OnlyHuntingId delete(int id) {
        Hunting hunting = huntingRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
        List<HuntingScrap> huntingScrapList = huntingScrapRepository.findAllByHunting(hunting);
        for (int idx = 0; idx < huntingScrapList.size(); idx++)
            huntingScrapRepository.deleteById(huntingScrapList.get(idx).getId());
        huntingRepository.deleteById(id);
        return HuntingResponse.OnlyHuntingId.build(hunting);
    }

    public HuntingResponse.OnlyHuntingScrapId createScrap(HuntingRequest.CreateHuntingScrap request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Hunting hunting = huntingRepository.findById(request.getHuntingId()).orElseThrow(HuntingNotFoundException::new);

        int cnt = huntingScrapRepository.countAllByUserAndHunting(user, hunting);
        if (cnt > 0) {
            HuntingScrap huntingScrap = huntingScrapRepository.findByUserAndHunting(user, hunting);
            return HuntingResponse.OnlyHuntingScrapId.build(huntingScrap);
        }

        HuntingScrap huntingScrap = request.toEntity(user, hunting);
        HuntingScrap savedHuntingScrap = huntingScrapRepository.save(huntingScrap);
        return HuntingResponse.OnlyHuntingScrapId.build(savedHuntingScrap);
    }

    public HuntingResponse.OnlyHuntingScrapId deleteScrap(int id) {
        HuntingScrap huntingScrap = huntingScrapRepository.findById(id).orElseThrow(HuntingNotFoundException::new);
        huntingScrapRepository.deleteById(id);
        return HuntingResponse.OnlyHuntingScrapId.build(huntingScrap);
    }
}
