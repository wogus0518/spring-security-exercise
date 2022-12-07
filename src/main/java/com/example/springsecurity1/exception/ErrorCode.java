package com.example.springsecurity1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "ID가 중복되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "ID, PW가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

}
