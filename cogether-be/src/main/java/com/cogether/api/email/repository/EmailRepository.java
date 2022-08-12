package com.cogether.api.email.repository;

import com.cogether.api.email.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Verification,Long> {

    Optional<Verification> searchByCode(String code);

    Optional<Verification> searchByEmail(String email);

    Optional<Verification> deleteByEmail(String email);

}
