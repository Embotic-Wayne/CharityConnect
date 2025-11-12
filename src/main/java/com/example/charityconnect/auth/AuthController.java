package com.example.charityconnect.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
        auth.register(req.email(), req.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest req) {
        var token = auth.login(req.email(), req.password());
        return new TokenResponse(token);
    }
}
