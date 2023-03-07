package com.ispan.pcbuy.controller;

import com.ispan.pcbuy.dto.PasswordUpdateRequest;
import com.ispan.pcbuy.dto.UserRegisterRequest;
import com.ispan.pcbuy.dto.UserUpdateRequest;
import com.ispan.pcbuy.model.User;
import com.ispan.pcbuy.service.UserDetailsService;
import com.ispan.pcbuy.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userService.getUsers();
        return ResponseEntity.status(HttpStatus.CREATED).body(userList);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        System.out.println("呼叫註冊功能");
        Integer userId = userService.register(userRegisterRequest);
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId,
                                         @RequestBody @Valid UserUpdateRequest userUpdateRequest){
        User user = userService.userUpdate(userId, userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/{userId}/pwdupdate")
    public ResponseEntity<HttpStatus> updatePwd(@PathVariable Integer userId,
                                       @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest){
        System.out.println("呼叫改密碼");
        userService.pwdUpdate(userId, passwordUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer userId,
                                                 @RequestParam String password){
        userService.deleteUser(userId, password);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(name);
//        System.out.println("目前的使用者" + name);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
