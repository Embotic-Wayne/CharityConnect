package com.example.charityconnect.web;

import com.example.charityconnect.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerWebTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean AuthService auth;

    @Test
    void register_returns200_and_invokes_service() throws Exception {
        // register(...) is void
        doNothing().when(auth).register(eq("u@x"), anyString());

        String body = """
      {"email":"u@x","password":"p"}
      """;

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())          // 200
                .andExpect(content().string(""));    // your endpoint returns empty body

        verify(auth).register(eq("u@x"), eq("p"));
    }

    @Test
    void login_ok_returnsToken() throws Exception {
        when(auth.login("u@x","p")).thenReturn("JWT");

        String body = """
      {"email":"u@x","password":"p"}
      """;

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())                  // 200 OK
                .andExpect(jsonPath("$.token").value("JWT"));
    }

    @Test
    void login_invalid_returns403() throws Exception {
        when(auth.login("u@x","bad"))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("bad"));

        String body = """
      {"email":"u@x","password":"bad"}
      """;

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());          // 403 in your app
    }
}
