package com.example.backend.service;


import com.example.backend.config.security.JwtAuthenticationResponse;
import com.example.backend.model.binding.UserLoginBindingModel;
import com.example.backend.model.service.UserRegisterServiceModel;

public interface UserService {
    void registerAndLoginUser(UserRegisterServiceModel model);

    boolean emailExists(String email);

    JwtAuthenticationResponse login(UserLoginBindingModel userLoginBindingModel);

}
