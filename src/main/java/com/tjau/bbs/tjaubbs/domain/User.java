package com.tjau.bbs.tjaubbs.domain;

import java.util.Date;

public class User {

    private String id;
    private String realname;
    private String email;
    private String username;
    private String college;
    private String sign;
    private String major;
    private String resume;
    private Date registerTime;
    private String avatar;
    private String password;
    private int gender;
    private int money;
    private int state;
    private int shenfen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getShenfen() {
        return shenfen;
    }

    public void setShenfen(int shenfen) {
        this.shenfen = shenfen;
    }
    public User(){}

    public User(String id, String realname, String email, String username, String sign, String college, String major, Date registerTime, String password, int shenfen) {
        this.major = major;
        this.sign = sign;
        this.id = id;
        this.realname = realname;
        this.email = email;
        this.username = username;
        this.college = college;
        this.registerTime = registerTime;
        this.password = password;
        this.shenfen = shenfen;
    }

    public User(String id, String realname, String email, String username, String college, Date registerTime, String password, int shenfen) {
        this.id = id;
        this.realname = realname;
        this.email = email;
        this.username = username;
        this.college = college;
        this.registerTime = registerTime;
        this.password = password;
        this.shenfen = shenfen;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", college='" + college + '\'' +
                ", sign='" + sign + '\'' +
                ", major='" + major + '\'' +
                ", resume='" + resume + '\'' +
                ", registerTime=" + registerTime +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", money=" + money +
                ", state=" + state +
                ", shenfen=" + shenfen +
                '}';
    }
}
