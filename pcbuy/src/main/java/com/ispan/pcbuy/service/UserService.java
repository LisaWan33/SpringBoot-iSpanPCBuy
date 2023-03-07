package com.ispan.pcbuy.service;

import com.ispan.pcbuy.dto.PasswordUpdateRequest;
import com.ispan.pcbuy.dto.UserLoginRequest;
import com.ispan.pcbuy.dto.UserRegisterRequest;
import com.ispan.pcbuy.dto.UserUpdateRequest;
import com.ispan.pcbuy.model.User;

import java.util.List;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    User login(UserLoginRequest UserLoginRequest);

    User userUpdate(Integer userId, UserUpdateRequest userUpdateRequest);

    void pwdUpdate(Integer userId , PasswordUpdateRequest passwordUpdateRequest);

    void deleteUser(Integer userId, String password);

    List<User> getUsers();
}
