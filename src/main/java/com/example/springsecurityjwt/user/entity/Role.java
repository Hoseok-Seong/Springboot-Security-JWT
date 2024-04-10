package com.example.springsecurityjwt.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "운영자"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;
}

