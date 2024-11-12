package com.example.library_management.repositories;

import com.example.library_management.entities.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {

    @Query("SELECT u FROM SessionToken u WHERE u.status = 'ACTIVE' AND u.token = :token")
    Optional<SessionToken> findByToken(@Param("token") String token);

}
