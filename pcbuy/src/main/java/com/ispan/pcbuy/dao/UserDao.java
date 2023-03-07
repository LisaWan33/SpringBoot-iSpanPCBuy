package com.ispan.pcbuy.dao;

import com.ispan.pcbuy.dto.PasswordUpdateRequest;
import com.ispan.pcbuy.dto.UserRegisterRequest;
import com.ispan.pcbuy.dto.UserUpdateRequest;
import com.ispan.pcbuy.model.User;

import java.util.List;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserByUsername(String username);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    void userUpdate(Integer userId, UserUpdateRequest userUpdateRequest);

    void pwdUpdate(Integer userId, String BCryptPasswordNew);

    void deleteUser(Integer userId);

    List<User> getUsers();
}
