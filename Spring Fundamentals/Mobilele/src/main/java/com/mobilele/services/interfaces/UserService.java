package com.mobilele.services.interfaces;

import com.mobilele.models.DTOs.UserLoginDTO;
import com.mobilele.models.DTOs.UserRegisterDTO;

public interface UserService {

    void registerUser(UserRegisterDTO userRegisterDTO);

    boolean login(UserLoginDTO userLoginDTO);

    void seedUsers();
}
