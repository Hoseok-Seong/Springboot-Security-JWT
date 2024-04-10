package com.example.springsecurityjwt.user.controller;

import com.example.springsecurityjwt.user.dto.NewAccessTokenReq;
import com.example.springsecurityjwt.user.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/access-token")
    public ResponseEntity<?> generateNewAccessToken(@RequestHeader("User-Agent") String userAgent,
                                                    @RequestBody @Valid NewAccessTokenReq newAccessTokenReq) {
        return tokenService.generateNewAccessToken(userAgent, newAccessTokenReq);
    }
}
