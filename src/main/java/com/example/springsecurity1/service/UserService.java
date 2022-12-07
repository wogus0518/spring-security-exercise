package com.example.springsecurity1.service;

import com.example.springsecurity1.domain.User;
import com.example.springsecurity1.domain.dto.UserDto;
import com.example.springsecurity1.domain.dto.UserJoinRequest;
import com.example.springsecurity1.exception.ErrorCode;
import com.example.springsecurity1.exception.UserException;
import com.example.springsecurity1.repository.UserRepository;
import com.example.springsecurity1.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMs = 1000 * 60 * 60;

    @Transactional
    public UserDto join(UserJoinRequest userJoinRequest) {

        //loginId 중복 체크
        userRepository.findByLoginId(userJoinRequest.getLoginId())
                        .ifPresent(user -> {
                            throw new UserException(ErrorCode.DUPLICATED_LOGIN_ID);
                        });

        //정상로직 - .save()
        User user = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        return user.toDto();
    }

    public String login(String id, String password) {
        //존재하는 id인지 check
        User user = userRepository.findByLoginId(id)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND));

        //id, pw 일치하는지 check
        boolean matches = encoder.matches(password, user.getPassword());
        if(!matches) throw new UserException(ErrorCode.INVALID_PASSWORD);

        //정상적으로 로그인 실행 + 토큰발행
        return JwtTokenUtil.createToken(id, secretKey, expireTimeMs);
    }
}
