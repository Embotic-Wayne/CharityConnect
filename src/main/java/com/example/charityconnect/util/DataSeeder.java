package com.example.charityconnect.util;

import com.example.charityconnect.domain.Role;
import com.example.charityconnect.domain.User;
import com.example.charityconnect.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

  @Bean
  CommandLineRunner seed(UserRepository users, PasswordEncoder encoder) {
    return args -> {
      if (users.findByEmail("admin@charity.local").isEmpty()) {
        User admin = new User();
        admin.setEmail("admin@charity.local");
        admin.setPassword(encoder.encode("admin123"));
        admin.setRoles(Set.of(Role.ADMIN));
        users.save(admin);
      }
    };
  }
}
