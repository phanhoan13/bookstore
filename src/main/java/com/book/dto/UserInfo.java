package com.book.dto;

import com.book.entity.User;

public class UserInfo {

    private String userName;
    private String password;

    public UserInfo() {

    }
    public UserInfo(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
    }

    // Không thay đổi Constructor này, nó được sử dụng trong Hibernate Query
    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


