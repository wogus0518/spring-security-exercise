package com.example.springsecurity1.controller;

import com.example.springsecurity1.domain.UserRole;
import com.example.springsecurity1.domain.dto.UserDto;
import com.example.springsecurity1.domain.dto.UserJoinRequest;
import com.example.springsecurity1.exception.ErrorCode;
import com.example.springsecurity1.exception.UserException;
import com.example.springsecurity1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private final String loginId = "testId";
    private final String password = "password";
    private final String email = "test@gmail.com";
    private final UserRole role = UserRole.USER;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @WithMockUser
    @DisplayName("회원가입 성공")
    void join() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest(loginId, password, email, role);
        UserDto userDto = new UserDto(1L, loginId, password, email, role);

        given(userService.join(any(UserJoinRequest.class))).willReturn(userDto);

        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userJoinRequest)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.loginId").value(loginId))
                .andExpect(jsonPath("$.result.password").doesNotExist())
                .andExpect(jsonPath("$.result.email").value(email))
                .andExpect(jsonPath("$.result.role").value(role.name()))
                .andDo(print());

        verify(userService).join(any(UserJoinRequest.class));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 실패 - 아이디 중복")
    void joinDuplicatedLoginId() throws Exception {

        String errorMessage = "UserException{errorCode=DUPLICATED_LOGIN_ID, message='ID가 중복되었습니다.'}";

        UserJoinRequest userJoinRequest = new UserJoinRequest(loginId, password, email, role);
        UserException userException = new UserException(ErrorCode.DUPLICATED_LOGIN_ID);

        when(userService.join(any(UserJoinRequest.class))).thenThrow(userException);

        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userJoinRequest)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andDo(print());
    }
}