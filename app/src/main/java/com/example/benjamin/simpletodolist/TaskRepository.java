package com.example.benjamin.simpletodolist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {
    private TasksDao tasksDao;
    private LiveData<List<Task_Table_Entity>> allTasks;

    public TaskRepository(Application application){
        MyRoomDB roomDB = MyRoomDB.getInstance(application);
        tasksDao = roomDB.tasksDao();
        allTasks = tasksDao.getTasks();
    }

    public void insert (Task_Table_Entity task){
        new InsertTaskAsyncTask(tasksDao).execute(task);
    }

    public void update(Task_Table_Entity task){
        new UpdateTaskAsyncTask(tasksDao).execute(task);
    }

    public void delete(Task_Table_Entity task){
        new DeleteTaskAsyncTask(tasksDao).execute(task);
    }

    public void deleteAllTasks(){
        new DeleteAllTasksAsyncTask(tasksDao).execute();
    }

    public LiveData<List<Task_Table_Entity>> getAllTasks() {
        return allTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task_Table_Entity, Void, Void>{
        private TasksDao tasksDao;

        private InsertTaskAsyncTask(TasksDao tasksDao){
            this.tasksDao = tasksDao;
        }

        @Override
        protected Void doInBackground(Task_Table_Entity... task_table_entities) {
            tasksDao.insert(task_table_entities[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task_Table_Entity, Void, Void>{
        private TasksDao tasksDao;

        private UpdateTaskAsyncTask(TasksDao tasksDao){
            this.tasksDao = tasksDao;
        }

        @Override
        protected Void doInBackground(Task_Table_Entity... task_table_entities) {
            tasksDao.update(task_table_entities[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task_Table_Entity, Void, Void>{
        private TasksDao tasksDao;

        private DeleteTaskAsyncTask(TasksDao tasksDao){
            this.tasksDao = tasksDao;
        }

        @Override
        protected Void doInBackground(Task_Table_Entity... task_table_entities) {
            tasksDao.delete(task_table_entities[0]);
            return null;
        }
    }

    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void>{
        private TasksDao tasksDao;

        private DeleteAllTasksAsyncTask(TasksDao tasksDao){
            this.tasksDao = tasksDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tasksDao.deleteAllTasks();
            return null;
        }
    }
}
