package com.example.charityconnect.config;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsBranchTest {

    // 44–48 chars → >32 bytes
    private static final String SECRET   = "test-secret-0123456789-0123456789-0123456789-01";
    private static final String SECRET_A = "secret-A-0123456789-0123456789-0123456789-0123";
    private static final String SECRET_B = "secret-B-0123456789-0123456789-0123456789-0123";

    @Test
    void parse_validToken_ok() {
        var jwt = new JwtUtils(SECRET, 60_000);
        var tok = jwt.generate("user@x", Map.of("roles","USER"));
        var body = jwt.parse(tok).getBody();
        assertEquals("user@x", body.getSubject());
        assertEquals("USER", body.get("roles"));
    }

    @Test
    void parse_expired_throws() throws InterruptedException {
        var jwt = new JwtUtils(SECRET, 1); // 1 ms
        var tok = jwt.generate("user@x", Map.of());
        Thread.sleep(5);
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> jwt.parse(tok));
    }

    @Test
    void parse_badSignature_throws() {
        var jwt1 = new JwtUtils(SECRET_A, 60_000);
        var jwt2 = new JwtUtils(SECRET_B, 60_000);
        var tok = jwt1.generate("user@x", Map.of());
        assertThrows(SignatureException.class, () -> jwt2.parse(tok));
    }
}
