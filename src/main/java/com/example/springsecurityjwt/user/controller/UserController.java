package com.example.springsecurityjwt.user.controller;

import com.example.springsecurityjwt.user.dto.UserJoinReq;
import com.example.springsecurityjwt.user.dto.UserLoginReq;
import com.example.springsecurityjwt.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("User-Agent") String userAgent, @RequestBody @Valid UserLoginReq userLoginReq) {
        return userService.login(userAgent, userLoginReq);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestHeader("User-Agent") String userAgent, @RequestBody @Valid UserJoinReq userJoinReq) {
        return userService.join(userAgent, userJoinReq);
    }
}
