package com.ispan.pcbuy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setCreateTableOnStartup(true); 自動創建Token資料表
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定義自己編寫的登入頁面
        http.formLogin()
                .loginPage("/login_register.html") //登入頁面設置
                .loginProcessingUrl("/users/login") //登入時訪問的URL 要和form表單的action相同
                .defaultSuccessUrl("/login_state.html",true) //登入後跳轉路徑
                .failureUrl("/login_state.html")
                .permitAll();
        //退出
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .permitAll();

        //自定義403沒有權限訪問頁面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        //設定訪問權限
        http.authorizeRequests()
                .antMatchers("/users/currentUser","/users/userinfo","/users/login","/users/register","/assets/**","index_assets/**","/index.html","/contact_us.html","/intro.html","/login_register.html","/rank.html","/unauth.html","/","/login.html","/products/**").permitAll() //設置哪些路徑可以直接訪問，不需要認證
//                    1.antMatchers("/loginSuccess").hasAuthority("role") //只賦予單個權限可以訪問
//                    2.antMatchers("/loginSuccess").hasAnyAuthority("role","manger") //賦予多個權限可以訪問
//                    3.antMatchers("/loginSuccess").hasRole("sale")
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/member/**").hasAnyRole("member","admin")
                .anyRequest().authenticated()
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository()) //設置記住我
                .tokenValiditySeconds(600) //設置記住我的有效時長(秒)
                .userDetailsService(userDetailsService)
                .and().csrf().disable(); //關閉 csrf 防護
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}