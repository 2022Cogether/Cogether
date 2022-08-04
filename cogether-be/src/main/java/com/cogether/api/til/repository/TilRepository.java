package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilRepository extends JpaRepository<Til, Integer> {
    List<Til> findAllByContentContainingIgnoreCaseOrTitleContainingIgnoreCaseOrderByCreatedAtDesc(String keyword, String keyword2);
}
