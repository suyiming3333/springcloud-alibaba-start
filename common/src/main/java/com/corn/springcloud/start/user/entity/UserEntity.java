package com.corn.springcloud.start.user.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
public class UserEntity{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;

    private String fullname;

    protected Serializable pkVal() {
        return this.id;
    }

    public UserEntity() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
