package com.pulse.auth.refresh.entity;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name ="idx_refresh_token_token", columnList = "token", unique = true),
                @Index(name = "idx_refresh_token_userId", columnList = "userId")
        }
)
public class RefreshTokenEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private boolean revoked;

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(String token, UUID userId, Instant expiresAt, Instant createdAt, boolean revoked) {
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
    }

    public RefreshTokenEntity(UUID id,String token, UUID userId, Instant expiresAt, Instant createdAt, boolean revoked) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
        return revoked == that.revoked && Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(userId, that.userId) && Objects.equals(expiresAt, that.expiresAt) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, userId, expiresAt, createdAt, revoked);
    }

    @Override
    public String toString() {
        return "RefreshTokenEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                ", revoked=" + revoked +
                '}';
    }
}
