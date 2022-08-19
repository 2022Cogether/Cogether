package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilLike;
import com.cogether.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilLikeRepository extends JpaRepository<TilLike, Integer> {
    Integer countAllByTil(Til til);

    Integer countAllByTilAndUser(Til til, User user);

    TilLike findByTilAndUser(Til til, User user);

    List<TilLike> findAllByTil(Til til);
}
