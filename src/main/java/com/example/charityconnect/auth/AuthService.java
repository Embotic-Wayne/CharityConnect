package com.example.charityconnect.auth;

import com.example.charityconnect.config.JwtUtils;
import com.example.charityconnect.domain.Role;
import com.example.charityconnect.domain.User;
import com.example.charityconnect.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authManager;
  private final JwtUtils jwt;

  public AuthService(UserRepository users, PasswordEncoder encoder,
                     AuthenticationManager authManager, JwtUtils jwt) {
    this.users = users; this.encoder = encoder; this.authManager = authManager; this.jwt = jwt;
  }

  public void register(String email, String rawPassword) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(encoder.encode(rawPassword));
    user.setRoles(Set.of(Role.USER));
    users.save(user);
  }

  public String login(String email, String password) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    var roles = users.findByEmail(email).get().getRoles();
    return jwt.generate(email, Map.of("roles", roles));
  }
}
