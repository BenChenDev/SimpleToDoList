package com.example.benjamin.simpletodolist;

public class task {
    private String task, dueDay;

    public task(String task, String dueDay) {
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
