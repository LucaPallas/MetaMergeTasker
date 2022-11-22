package com.example.metaMergeTasker;

public class Task {
    private String taskName;
    private int status;
    private int id;
    private int deleted;

    public Task()
    {
        //this.taskName=null;
        //this.status=0;
        //this.deleted=0;
    }
    public Task(String taskName, int status, int deleted) {
        super();
        this.taskName = taskName;
        this.status = status;
        this.deleted = deleted;
    }
    public int getId() {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public int getDeleted() {
        return deleted;
    }
    public void setDeleted(int deleted) { this.deleted = deleted; }
}