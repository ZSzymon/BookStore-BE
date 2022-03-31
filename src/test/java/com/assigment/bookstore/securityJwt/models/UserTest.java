package com.assigment.bookstore.securityJwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


class UserTest {





    @Test
    void createWithConstructor(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("user", "user@gmail.com", encoder.encode("password"));
    }
}