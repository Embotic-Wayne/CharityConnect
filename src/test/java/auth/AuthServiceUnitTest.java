package auth;

import com.example.charityconnect.auth.AuthService;
import com.example.charityconnect.config.JwtUtils;
import com.example.charityconnect.domain.Role;
import com.example.charityconnect.domain.User;
import com.example.charityconnect.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceUnitTest {

  @Test
  void register_encodesPassword() {
    var repo = mock(UserRepository.class);
    var enc  = mock(PasswordEncoder.class);
    var am   = mock(AuthenticationManager.class);
    var jwt  = mock(JwtUtils.class);

    when(enc.encode("x")).thenReturn("ENC");

    var svc = new AuthService(repo, enc, am, jwt);
    svc.register("a@b.c", "x");

    verify(repo).save(argThat((User u) ->
        "a@b.c".equals(u.getEmail())
        && "ENC".equals(u.getPassword())
        && u.getRoles() != null
        && u.getRoles().contains(Role.USER)
    ));
  }

  @Test
  void login_returnsToken() {
    var repo = mock(UserRepository.class);
    var enc  = mock(PasswordEncoder.class);
    var am   = mock(AuthenticationManager.class);
    var jwt  = mock(JwtUtils.class);

    var user = new User();
    user.setId(1L);
    user.setEmail("a@b.c");
    user.setPassword("P");
    user.setRoles(Set.of(Role.USER));

    when(repo.findByEmail("a@b.c")).thenReturn(Optional.of(user));
    when(jwt.generate(any(), any())).thenReturn("TOKEN");

    var svc = new AuthService(repo, enc, am, jwt);
    var token = svc.login("a@b.c", "x");
    assertEquals("TOKEN", token);
  }
}
