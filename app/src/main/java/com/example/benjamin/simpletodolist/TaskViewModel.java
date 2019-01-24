package com.example.benjamin.simpletodolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task_Table_Entity>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(Task_Table_Entity task){
        repository.insert(task);
    }

    public void update (Task_Table_Entity task){
        repository.update(task);
    }

    public void delete (Task_Table_Entity task){
        repository.delete(task);
    }

    public void deleteAllTasks(){
        repository.deleteAllTasks();
    }

    public LiveData<List<Task_Table_Entity>> getAllTasks() {
        return allTasks;
    }
}
