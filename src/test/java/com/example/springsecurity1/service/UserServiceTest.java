package com.example.springsecurity1.service;

import com.example.springsecurity1.domain.User;
import com.example.springsecurity1.domain.UserRole;
import com.example.springsecurity1.domain.dto.UserDto;
import com.example.springsecurity1.domain.dto.UserJoinRequest;
import com.example.springsecurity1.exception.ErrorCode;
import com.example.springsecurity1.exception.UserException;
import com.example.springsecurity1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(UserServiceTest.class)
class UserServiceTest {

    private final String loginId = "testId";
    private final String password = "password";
    private final String email = "test@gmail.com";
    private final UserRole role = UserRole.USER;

    @MockBean
    BCryptPasswordEncoder encoder;

    @MockBean
    UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUpTest() {
        userService = new UserService(userRepository, encoder);
    }

    @Test
    @WithMockUser
    @DisplayName("join() 성공")
    void join() {
        UserJoinRequest userJoinRequest = new UserJoinRequest(loginId, password, email, role);

        Mockito.when(userRepository.save(any(User.class))).then(returnsFirstArg());
        UserDto userDto = userService.join(userJoinRequest);

        assertEquals(userDto.getLoginId(), loginId);
    }

    @Test
    @WithMockUser
    @DisplayName("join() 실패 - 중복된 loginId 존재")
    void joinFailDuplicatedLoginId() {
        UserJoinRequest userJoinRequest = new UserJoinRequest(loginId, password, email, role);
        User user = new User(1L, loginId, password, email, role);

        Mockito.when(userRepository.findByLoginId(userJoinRequest.getLoginId())).thenReturn(Optional.of(user));

        UserException exception = assertThrows(UserException.class, () -> userService.join(userJoinRequest));
        assertEquals(exception.getErrorCode(), ErrorCode.DUPLICATED_LOGIN_ID);
    }
}