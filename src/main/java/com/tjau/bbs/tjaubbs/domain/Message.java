package com.tjau.bbs.tjaubbs.domain;

public class Message {

    private String id;
    private String userAId;
    private User userA;
    private String userBId;
    private User userB;
    private String taskId;
    private Task task;
    private String detail;
    private int type;
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAId() {
        return userAId;
    }

    public void setUserAId(String userAId) {
        this.userAId = userAId;
    }

    public String getUserBId() {
        return userBId;
    }

    public void setUserBId(String userBId) {
        this.userBId = userBId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", userAId='" + userAId + '\'' +
                ", userBId='" + userBId + '\'' +
                ", detail='" + detail + '\'' +
                ", type=" + type +
                ", state=" + state +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
