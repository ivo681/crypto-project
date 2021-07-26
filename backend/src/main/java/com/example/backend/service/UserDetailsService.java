package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException("User with email " + email + " was not found"));
        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())
        ).collect(Collectors.toList());

        UserDetails result= new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities
        );

        return result;
    }
}
