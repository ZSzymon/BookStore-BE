package com.assigment.bookstore;

import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.payload.request.LoginRequest;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
import com.assigment.bookstore.securityJwt.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static com.assigment.bookstore.util.asJsonString;
import static com.mongodb.assertions.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookStoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStoreApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loadContext(){

    }
    @Test
    public void givenDataIsJson_whenDataIsPostedByPostForObject_thenResponseBodyIsNotNull()
            throws IOException {
        LoginRequest loginRequest = new LoginRequest("admin", "password");
        HttpEntity<String> request =
                new HttpEntity<>(asJsonString(loginRequest));
        String loginUrl = "http://localhost:8080/api/auth/signin";
        String response =
                restTemplate.postForObject(loginUrl, request, String.class);
        JsonNode root = new ObjectMapper().readTree(response);

        assertNotNull(root);

    }


}
