package com.example.springsecurityjwt.user.repository;

import com.example.springsecurityjwt.user.entity.RefreshTokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenRedis, Long> {
    Optional<RefreshTokenRedis> findByRefreshToken(String refreshToken);
}
