package com.example.benjamin.simpletodolist;

public class Task {
    private String task, dueDay;

    public Task(String task, String dueDay) {
        this.task = task;
        this.dueDay = dueDay;
    }

    public String getTask() {
        return task;
    }

    public String getDueDay() {
        return dueDay;
    }
}
