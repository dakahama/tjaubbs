package com.tjau.bbs.tjaubbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Task {

    private String id;
    //对应数据库中的userId
    private String userId;
    private User user;
    private Date publicDate;
    private Date endDate;
    private String title;
    private String keyword;
    private String detail;
    private int money;
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", publicDate=" + publicDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", detail='" + detail + '\'' +
                ", money=" + money +
                ", state=" + state +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
