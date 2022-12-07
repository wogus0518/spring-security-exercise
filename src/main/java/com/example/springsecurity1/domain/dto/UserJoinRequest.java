package com.example.springsecurity1.domain.dto;

import com.example.springsecurity1.domain.User;
import com.example.springsecurity1.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private final String loginId;
    private final String password;
    private final String email;
    private final UserRole role;

    public User toEntity(String password) {
        return User.builder()
                .loginId(this.loginId)
                .password(password)
                .email(this.email)
                .role(this.role)
                .build();
    }

}
