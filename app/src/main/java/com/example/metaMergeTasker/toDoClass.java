package com.example.metaMergeTasker;

public class toDoClass {
    private String taskName;
    private Integer status;
    private int id;
    private Integer deleted;

    public toDoClass() {
        this.taskName=null;
        this.status=0;
        this.deleted=0;
    }

    public toDoClass(String taskName, Integer status, Integer deleted) {
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
    public Integer getStatus() {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public Integer getDeleted() {
        return deleted;
    }
    public void setDeleted(int deleted) { this.deleted = deleted; }
}