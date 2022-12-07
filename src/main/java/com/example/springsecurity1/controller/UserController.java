package com.example.springsecurity1.controller;

import com.example.springsecurity1.domain.Response;
import com.example.springsecurity1.domain.dto.*;
import com.example.springsecurity1.exception.UserException;
import com.example.springsecurity1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        try {
            UserDto userDto = userService.join(userJoinRequest);
            return Response.success(new UserJoinResponse(userDto.getId(), userDto.getLoginId(), userDto.getEmail(), userDto.getRole()));

        } catch (UserException e) {
            return Response.error(e.toString());
        }
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            String token = userService.login(userLoginRequest.getLoginId(), userLoginRequest.getPassword());
            return Response.success(new UserLoginResponse(token));
        } catch (UserException e) {
            return Response.error(e.toString());
        }
    }
}
