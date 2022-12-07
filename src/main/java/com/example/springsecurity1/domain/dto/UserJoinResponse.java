package com.example.springsecurity1.domain.dto;

import com.example.springsecurity1.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private final Long id;
    private final String loginId;
    private final String email;
    private final UserRole role;
}
