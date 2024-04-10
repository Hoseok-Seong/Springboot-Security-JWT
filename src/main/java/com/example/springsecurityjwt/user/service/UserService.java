package com.example.springsecurityjwt.user.service;

import com.example.springsecurityjwt.global.jwt.JwtProvider;
import com.example.springsecurityjwt.user.dto.UserJoinReq;
import com.example.springsecurityjwt.user.dto.UserJoinResp;
import com.example.springsecurityjwt.user.dto.UserLoginReq;
import com.example.springsecurityjwt.user.dto.UserLoginResp;
import com.example.springsecurityjwt.user.entity.User;
import com.example.springsecurityjwt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<?> login(String userAgent, UserLoginReq userLoginReq) {
        User user = userRepository
                .findByUsername(userLoginReq.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        if (passwordEncoder.matches(userLoginReq.getPassword(), user.getPassword())) {
            String accessToken = JwtProvider.createAccessToken(user);
            return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, accessToken).body(new UserLoginResp(user));
        }

        throw new RuntimeException("로그인 로직 실패");
    }

    @Transactional
    public ResponseEntity<?> join(String userAgent, UserJoinReq userJoinReq) {
        String rawPassword = userJoinReq.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userJoinReq.setPassword(encPassword);

        Optional<User> userOptional = userRepository.findByUsername(userJoinReq.getUsername());

        if (userOptional.isPresent()) {
            throw new DataIntegrityViolationException("사용자가 이미 존재합니다");
        }

        userRepository.save(userJoinReq.toEntity());

        User user = userRepository
                .findByUsername(userJoinReq.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            String accessToken = JwtProvider.createAccessToken(user);
            String refreshToken = JwtProvider.createRefreshToken(user);

            return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, accessToken)
                    .header(JwtProvider.REFRESH_TOKEN_HEADER, refreshToken).body(new UserJoinResp(user));
        }

        throw new RuntimeException("회원가입 로직 실패");
    }
}
