package com.example.benjamin.simpletodolist;

public class Task {
    private String task, dueDay, repeat;

    public Task(String task, String dueDay, String repeat) {
        this.task = task;
        this.dueDay = dueDay;
        this.repeat = repeat;
    }

    public String getTask() {
        return task;
    }

    public String getDueDay() {
        return dueDay;
    }

    public String getRepeat() {
        return repeat;
    }
}
