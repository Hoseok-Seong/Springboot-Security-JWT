package com.example.springsecurityjwt.user.dto;

import com.example.springsecurityjwt.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginReq {
    @NotBlank(message = "username을 입력해주세요")
    @Size(min = 7, max = 50)
    private String username;
    @NotBlank(message = "password를 입력해주세요")
    @Size(min = 8, max = 50)
    private String password;

    public UserLoginReq(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
