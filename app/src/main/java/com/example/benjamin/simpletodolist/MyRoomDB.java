package com.example.benjamin.simpletodolist;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Task_Table_Entity.class}, version = 1)
public abstract class MyRoomDB extends RoomDatabase{

    private static MyRoomDB instance;

    public abstract TasksDao tasksDao();

    public static  synchronized MyRoomDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyRoomDB.class, "tasksDB1")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private TasksDao tasksDao;

        private PopulateDbAsyncTask(MyRoomDB db){
            tasksDao = db.tasksDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tasksDao.insert(new Task_Table_Entity("Test task 1", "Today."));
            return null;
        }
    }
}
