package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilImgRepository extends JpaRepository<TilImg, Integer> {
    List<TilImg> findAllByTil(Til til);
}
