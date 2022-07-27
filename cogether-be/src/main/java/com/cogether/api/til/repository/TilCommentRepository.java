package com.cogether.api.til.repository;

import com.cogether.api.til.domain.TilComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TilCommentRepository extends JpaRepository<TilComment, Integer> {

}
