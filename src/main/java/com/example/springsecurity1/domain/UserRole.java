package com.example.springsecurity1.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN, USER;

    @JsonCreator
    public static UserRole from(String s) {
        return UserRole.valueOf(s.toUpperCase());
    }
}
