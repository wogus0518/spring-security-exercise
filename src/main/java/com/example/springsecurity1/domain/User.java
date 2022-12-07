package com.example.springsecurity1.domain;

import com.example.springsecurity1.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String loginId;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserDto toDto() {
        return UserDto.builder()
                .id(this.id)
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }
}
