package com.pulse.auth.refresh.repository;

import com.pulse.auth.refresh.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUserId(UUID userId);

    List<RefreshTokenEntity> findByUserIdAndRevoked(UUID userId, boolean revoked);
}
