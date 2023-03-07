package com.ispan.pcbuy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;

@Data
public class User {

    private Integer userId;
    private String username;

    @JsonIgnore
    private String password;

    private String name;
    private String tel;
    private String addr;

//    @JsonProperty("e-mail")
    private String email;

    private String auth;
    private Date createdDate;
    private Date lastModifiedDate;

    public User(String username, String password, String auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }

    public User() {
    }
}
