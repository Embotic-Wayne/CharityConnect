package com.example.charityconnect.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthFilter extends GenericFilter {
    private final JwtUtils jwt;
    private final UserDetailsService uds;

    public JwtAuthFilter(JwtUtils jwt, UserDetailsService uds) {
        this.jwt = jwt; this.uds = uds;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            var token = auth.substring(7);
            try {
                var jws = jwt.parse(token);
                var email = jws.getBody().getSubject();
                var userDetails = uds.loadUserByUsername(email);
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ignored) {}
        }
        chain.doFilter(request, response);
    }
}
