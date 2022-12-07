package com.example.springsecurity1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String status;
    private T result;
    private String message;

    public static <T> Response<T> success(T result) {
        return Response.<T>builder()
                .status("SUCCESS")
                .result(result)
                .build();
    }

    public static <T> Response<T> error(String message) {
        return Response.<T>builder()
                .status("ERROR")
                .message(message)
                .build();
    }
}
