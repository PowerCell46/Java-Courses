package com.springweb.services.interfaces;

import com.springweb.models.DTOs.UserRequestDTO;


public interface UserService {

    boolean hasInitializedExRates();

    UserRequestDTO[] fetchUsers();

    void seedUsers();

    UserRequestDTO getUserById(int id);
}
