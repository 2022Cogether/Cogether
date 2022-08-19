package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilCommentRepository extends JpaRepository<TilComment, Integer> {
    List<TilComment> findAllByTil(Til til);
}
