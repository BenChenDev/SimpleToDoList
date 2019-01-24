package com.example.benjamin.simpletodolist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task_Table_Entity>>() {
            @Override
            public void onChanged(@Nullable List<Task_Table_Entity> task_table_entities) {
                //update RecyclerView later
            }
        });
    }
//    ImageButton addTaskButton;
//    TextView addNewTask;
//
//    public static MyRoomDB DB;
//
//    //recyclerview
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        DB = Room.databaseBuilder(getApplicationContext(), MyRoomDB.class, "tasksDB1").allowMainThreadQueries().build();
//        int rowCount = DB.tasksDao().getCount();
//        if(rowCount > 0){
//            setContentView(R.layout.main_activity_layout_2);
//
//            //recyclerview
//            recyclerView = findViewById(R.id.recyclerView);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            displayRecordSet();
//        } else {
//            setContentView(R.layout.activity_main);
//            addNewTask = findViewById(R.id.addNewTask);
//            addNewTask.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    openMain2Activity();
//                }
//            });
//        }
//
//        Toolbar topToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(topToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        addTaskButton = findViewById(R.id.blueAddButton);
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMain2Activity();
//            }
//        });
//    }
//
//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        int rowCount = DB.tasksDao().getCount();
//        if(rowCount > 0){
//            setContentView(R.layout.main_activity_layout_2);
//            //recyclerview
//            recyclerView = findViewById(R.id.recyclerView);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            displayRecordSet();
//        } else {
//            setContentView(R.layout.activity_main);
//        }
//
//        addTaskButton = findViewById(R.id.blueAddButton);
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMain2Activity();
//            }
//        });
//    }
//
//    public void openMain2Activity(){
//        Intent intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);
//    }
//
//    // Display an entire recordset to the screen.
//    public void displayRecordSet() {
//        List<Task_Table_Entity> tasks = DB.tasksDao().getTasks();
//
//        // Update the list view
//        adapter = new MyAdapter(tasks, this);
//        recyclerView.setAdapter(adapter);
//    }
//
////    @Override
////    public boolean onContextItemSelected(MenuItem item) {
////        return super.onContextItemSelected(item);
////    }
}
