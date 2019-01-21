package com.example.benjamin.simpletodolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageButton addTaskButton;
    TextView addNewTask;

    DBAdapter myDb;

    private List<Task> tasks;

    //recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();
        if(countTask() > 0){
            setContentView(R.layout.main_activity_layout_2);
            Cursor cursor = myDb.getAllRows();
            displayRecordSet(cursor);
        } else {
            setContentView(R.layout.activity_main);
            addNewTask = findViewById(R.id.addNewTask);
            addNewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openMain2Activity();
                }
            });
        }

        Toolbar topToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        addTaskButton = findViewById(R.id.blueAddButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain2Activity();
            }
        });



        //recyclerview
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void openMain2Activity(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    private void openDB(){
        myDb = new DBAdapter(this);
        myDb.open();
    }

    //check total records
    private int countTask(){
        Cursor cursor = myDb.getAllRows();
        return cursor.getCount();
    }

    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {
        tasks = new ArrayList<>();
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String t = cursor.getString(DBAdapter.COL_TASK);
                String dueDay = cursor.getString(DBAdapter.COL_DUEDAY);
                String repeat = cursor.getString(DBAdapter.COL_REPEAT);

                Task task = new Task(
                        "task is " + t,
                        "due day is " + dueDay,
                        "repeat" + repeat
                );

                tasks.add(task);

                // Append data to the message:
//                message += "id=" + id
//                        +", first name=" + firstname
//                        +", last name=" + lastname
//                        +", marks=" + marks
//                        +"\n";

            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        // Update the list view
        adapter = new MyAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        // Display a Toast message
        //Log.d("Test", message);
    }
}
