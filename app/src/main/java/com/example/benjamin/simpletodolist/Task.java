package com.example.benjamin.simpletodolist;

public class Task {
    private String id;
    private String task;
    private String due_day;

    public Task(){}

    public Task(String id, String task, String due_day) {
        this.id = id;
        this.task = task;
        this.due_day = due_day;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDue_day(String due_day) {
        this.due_day = due_day;
    }

    public String getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDue_day() {
        return due_day;
    }
}
