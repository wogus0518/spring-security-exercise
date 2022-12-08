package com.example.springsecurity1.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EncrypterConfig.class)
class EncrypterConfigTest {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("encoder 테스트")
    void encoding() {
        String password = "password";
        String encode = encoder.encode(password);

        assertTrue(encoder.matches(password, encode));
    }
}