package com.example.springsecurityjwt.user.service;

import com.example.springsecurityjwt.global.jwt.JwtProvider;
import com.example.springsecurityjwt.user.dto.NewAccessTokenReq;
import com.example.springsecurityjwt.user.dto.NewAccessTokenResp;
import com.example.springsecurityjwt.user.entity.RefreshTokenRedis;
import com.example.springsecurityjwt.user.entity.User;
import com.example.springsecurityjwt.user.repository.RefreshTokenRedisRepository;
import com.example.springsecurityjwt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public ResponseEntity<?> generateNewAccessToken(String userAgent, NewAccessTokenReq newAccessTokenReq) {
        User user = userRepository
                .findById(newAccessTokenReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        // 사용자의 Refresh 토큰이 Redis DB에 존재하지 않을 경우
        RefreshTokenRedis refreshTokenRedis = refreshTokenRedisRepository.findById((newAccessTokenReq.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("Refresh 토큰의 유효기간이 만료되었거나 존재하지 않습니다"));

        // 사용자의 Refresh 토큰이 존재하지만 요청한 Refresh 토큰과 일치하지 않을 경우
        if (!refreshTokenRedis.getRefreshToken().equals(newAccessTokenReq.getRefreshToken())) {
            // 토큰 탈취의 가능성이 있으므로 Redis DB에서 삭제
            refreshTokenRedisRepository.findById(user.getId())
                    .ifPresent(refreshTokenRedisRepository::delete);
            ResponseEntity.badRequest().body("Refresh 토큰이 일치하지 않습니다. 다시 로그인해주세요.");
        }

        String accessToken = JwtProvider.createAccessToken(user);

        // RTR 방식 사용
        String newRefreshToken = JwtProvider.createRefreshToken(user);
        refreshTokenRedis.updateRefreshToken(newRefreshToken);

        return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, accessToken)
                .header(JwtProvider.REFRESH_TOKEN_HEADER, newRefreshToken).body(new NewAccessTokenResp(user));
    }
}
