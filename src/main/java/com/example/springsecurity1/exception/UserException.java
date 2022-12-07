package com.example.springsecurity1.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String toString() {
        return "UserException{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
