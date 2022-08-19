package com.cogether.api.hunting.repository;

import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.hunting.domain.HuntingScrap;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntingScrapRepository extends JpaRepository<HuntingScrap, Integer> {
    int countAllByUserAndHunting(User user, Hunting hunting);

    HuntingScrap findByUserAndHunting(User user, Hunting hunting);

    List<HuntingScrap> findAllByHunting(Hunting hunting);
}
