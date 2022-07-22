package com.cogether.api.til.repository;

import com.cogether.api.til.dto.Til;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TilRepository extends JpaRepository<Til, Integer> {

}
