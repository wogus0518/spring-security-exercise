package com.example.springsecurity1.domain.dto;

import com.example.springsecurity1.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private UserRole role;
}
