package com.example.charityconnect.auth;

import com.example.charityconnect.repo.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository users;
    public UserDetailsServiceImpl(UserRepository users) { this.users = users; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var authorities = u.getRoles().stream()
                .map(r -> "ROLE_" + r.name()).map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .toList();
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), authorities);
    }
}
