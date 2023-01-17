package com.example;

import java.util.ArrayList;

public class User {
    private String userName;
    private String userPhone;
    private String photo; //最后聊天时间


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getPhoto() {
        return photo;
    }

}
