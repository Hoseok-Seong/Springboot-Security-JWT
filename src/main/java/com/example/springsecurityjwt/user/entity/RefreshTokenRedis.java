package com.example.springsecurityjwt.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash("refreshToken")
public class RefreshTokenRedis {
    @Id
    private Long id;
    @Column(nullable = false)
    private String refreshToken;

    @TimeToLive
    private Long expiredAt;

    @Builder
    public RefreshTokenRedis(Long id, String refreshToken, Long expiredAt) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
