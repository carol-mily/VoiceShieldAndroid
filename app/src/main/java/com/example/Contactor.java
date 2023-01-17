package com.example;

public class Contactor {
    private String nickName;
    private String phone;
    private String lastTime;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
