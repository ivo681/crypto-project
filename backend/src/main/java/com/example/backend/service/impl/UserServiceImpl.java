package com.example.backend.service.impl;

import com.example.backend.config.security.JwtAuthenticationResponse;
import com.example.backend.config.security.JwtTokenProvider;
import com.example.backend.config.security.UserPrincipal;
import com.example.backend.model.User;
import com.example.backend.model.UserRole;
import com.example.backend.model.binding.UserLoginBindingModel;
import com.example.backend.model.enums.RoleEnum;
import com.example.backend.model.service.UserRegisterServiceModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRoleRepository;
import com.example.backend.service.UserDetailsService;
import com.example.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public void registerAndLoginUser(UserRegisterServiceModel model) {
        User user = this.modelMapper.map(model, User.class);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        ArrayList<UserRole> roles = new ArrayList<>();
        if (userRepository.count() == 0) {
            roles.add(this.userRoleRepository.findByRole(RoleEnum.ADMIN).get());
        }
        roles.add(this.userRoleRepository.findByRole(RoleEnum.USER).get());
        user.setRoles(roles);
        this.userRepository.save(user);
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean emailExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    @Override
    public JwtAuthenticationResponse login(UserLoginBindingModel userLoginBindingModel) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginBindingModel.getEmail(),
                        userLoginBindingModel.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return new JwtAuthenticationResponse(jwt, userPrincipal);
    }
}
