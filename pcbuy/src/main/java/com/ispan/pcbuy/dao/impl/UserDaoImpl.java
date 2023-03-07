package com.ispan.pcbuy.dao.impl;

import com.ispan.pcbuy.dao.UserDao;
import com.ispan.pcbuy.dto.PasswordUpdateRequest;
import com.ispan.pcbuy.dto.UserRegisterRequest;
import com.ispan.pcbuy.dto.UserUpdateRequest;
import com.ispan.pcbuy.model.Product;
import com.ispan.pcbuy.model.User;
import com.ispan.pcbuy.rowmapper.ProductRowMapper;
import com.ispan.pcbuy.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user (username, password, `name`, tel, addr, email , auth, created_date, last_modified_date)" +
                     "VALUES (:username, :password, :name , :tel, :addr, :email, :auth, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("username", userRegisterRequest.getUsername());
        map.put("password", userRegisterRequest.getPassword());
        map.put("name", userRegisterRequest.getName());
        map.put("tel", userRegisterRequest.getTel());
        map.put("addr", userRegisterRequest.getAddr());
        map.put("email", userRegisterRequest.getEmail());
        map.put("auth", "ROLE_member,ROLE_admin");

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = :username";
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM user WHERE user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT  * FROM user WHERE email = :email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void userUpdate(Integer userId, UserUpdateRequest userUpdateRequest) {
        String sql = "UPDATE user SET `name` = :name, tel = :tel, addr = :addr, email = :email, last_modified_date = :lastModifiedDate " +
                "WHERE user_id = :userId ";

        Map<String, Object> map = new HashMap<>();
        map.put("name", userUpdateRequest.getName());
        map.put("tel", userUpdateRequest.getTel());
        map.put("addr", userUpdateRequest.getAddr());
        map.put("email", userUpdateRequest.getEmail());
        map.put("userId", userId);
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void pwdUpdate(Integer userId, String BCryptPasswordNew) {
        String sql = "UPDATE user SET password = :password WHERE user_id = :userId ";

        Map<String, Object> map = new HashMap<>();
        map.put("password", BCryptPasswordNew);
        map.put("userId", userId);
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteUser(Integer userId) {
        String sql = "DELETE FROM user WHERE user_id = :userId ";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM user";

        Map<String, Object> map = new HashMap<>();

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0){
            return userList;
        }else {
            return null;
        }
    }
}
