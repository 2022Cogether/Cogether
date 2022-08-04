package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilLike;
import com.cogether.api.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TilLikeRepository extends JpaRepository<TilLike, Integer> {
    Integer countAllByTil (Til til);
    Integer countAllByTilAndUser(Til til, User user);
}
