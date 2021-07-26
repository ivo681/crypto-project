package com.example.backend.service;


import com.example.backend.model.service.UserRegisterServiceModel;

public interface UserService {
    void registerAndLoginUser(UserRegisterServiceModel model);

    boolean emailExists(String email);
}
