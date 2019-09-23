package com.tjau.bbs.tjaubbs.domain;

public class Contract {

    private String id;
    private String taskId;
    private Task task;
    private String userAId;
    private User userA;
    private String taskTitle;
    private String userBId;
    private User userB;
    private int state;
    private String evaluateA;
    private String evaluateB;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEvaluateA() {
        return evaluateA;
    }

    public void setEvaluateA(String evaluateA) {
        this.evaluateA = evaluateA;
    }

    public String getEvaluateB() {
        return evaluateB;
    }

    public void setEvaluateB(String evaluateB) {
        this.evaluateB = evaluateB;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}
