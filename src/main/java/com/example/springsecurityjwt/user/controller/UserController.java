package com.example.springsecurityjwt.user.controller;

import com.example.springsecurityjwt.global.security.MyUserDetails;
import com.example.springsecurityjwt.user.dto.UserJoinReq;
import com.example.springsecurityjwt.user.dto.UserLoginReq;
import com.example.springsecurityjwt.user.dto.UserUpdateReq;
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

    @PostMapping("/api/user/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                    @RequestBody @Valid UserUpdateReq userUpdateReq) {
        return userService.update(myUserDetails, userUpdateReq);
    }

    @PostMapping("/api/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        return ResponseEntity.ok().body("테스트 통과");
    }
}
