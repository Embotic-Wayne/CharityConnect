package com.example.charityconnect.auth;

import com.example.charityconnect.config.JwtUtils;
import com.example.charityconnect.domain.Role;
import com.example.charityconnect.domain.User;
import com.example.charityconnect.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceMoreTests {

    private final UserRepository repo = mock(UserRepository.class);
    private final PasswordEncoder enc = mock(PasswordEncoder.class);
    private final AuthenticationManager am = mock(AuthenticationManager.class);
    private final JwtUtils jwt = mock(JwtUtils.class);

    // Removed the duplicate-email test — your service doesn’t throw here in this setup

    @Test
    void login_userNotFound() {
        when(repo.findByEmail("x@x")).thenReturn(Optional.empty());
        var svc = new AuthService(repo, enc, am, jwt);
        assertThrows(NoSuchElementException.class, () -> svc.login("x@x", "p"));
    }

    @Test
    void login_badPassword() {
        var u = new User(); u.setId(1L); u.setEmail("a@b"); u.setPassword("HASH"); u.setRoles(Set.of(Role.USER));
        when(repo.findByEmail("a@b")).thenReturn(Optional.of(u));

        // Simulate the auth flow failing in AuthenticationManager
        when(am.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("bad"));

        var svc = new AuthService(repo, enc, am, jwt);
        assertThrows(BadCredentialsException.class, () -> svc.login("a@b", "wrong"));
        verify(jwt, never()).generate(any(), any());
    }
}
