//package com.assigment.bookstore.securityJwt.controllers;
//
//import static com.assigment.bookstore.util.asJsonString;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//import com.assigment.bookstore.securityJwt.payload.request.LoginRequest;
//import com.assigment.bookstore.securityJwt.repository.RoleRepository;
//import com.assigment.bookstore.securityJwt.repository.UserRepository;
//import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
//import com.assigment.bookstore.securityJwt.security.jwt.JwtUtils;
//import com.assigment.bookstore.securityJwt.security.services.UserDetailsServiceImpl;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Import;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.http.MediaType;
//import org.springframework.plugin.core.SimplePluginRegistry;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//
//    @Autowired
//    private MockMvc mvc;
//
//
//
//    @MockBean
//    UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @MockBean
//    AuthEntryPointJwt authEntryPointJwt;
//
//    @MockBean
//    RoleRepository roleRepository;
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    JwtUtils jwtUtils;
//
//    @Test
//    void authenticateUser() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api/auth/signin")
//                        .content(asJsonString(new LoginRequest("user", "password")))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print()).andExpect(status().isOk());
//    }
//
//
//    @Test
//    void registerUser() {
//    }
//}