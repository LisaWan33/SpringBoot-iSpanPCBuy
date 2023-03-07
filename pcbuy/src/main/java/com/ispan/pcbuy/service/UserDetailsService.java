package com.ispan.pcbuy.service;

import com.ispan.pcbuy.dao.UserDao;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{
    @Autowired
    private UserDao userDao;

    @Setter
    private String username;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        QueryWrapper<Users_old> wrapper = new QueryWrapper<>(); //硅谷老師教法
//        wrapper.eq("username", username);
//        Users_old users = usersMapper.selectOne(wrapper);
        System.out.println("loadUserByUsername()被呼叫了");
        String queryUsername  = userDao.getUserByUsername(username).getUsername();
        String queryPassword  = userDao.getUserByUsername(username).getPassword();
        String queryAuthority = userDao.getUserByUsername(username).getAuth();
        if(queryUsername == null){ //資料庫沒有這個使用者，認證失敗
            throw new UsernameNotFoundException("此帳號不存在");
        }
        //從資料庫查詢的users物件，獲取帳號密碼，再回傳
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(queryAuthority);
        return new User(queryUsername, queryPassword, auths);
    }
}
