package com.example.benjamin.simpletodolist;

import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener{
    private ImageButton addTaskButton, mic;
    private TextView addNewTask;
    private ArrayList<String> arrl;
    public static int VOICE_RECOGNITION = 2;
    public static MyRoomDB DB;

    //recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = Room.databaseBuilder(getApplicationContext(), MyRoomDB.class, "tasksDB1").allowMainThreadQueries().build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int rowCount = DB.tasksDao().getCount();
        if(rowCount > 0){
            setContentView(R.layout.main_activity_layout_2);
            //recyclerview
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            displayRecordSet();
        } else {
            setContentView(R.layout.activity_main);
            arrl = new ArrayList<String>();
            mic = findViewById(R.id.mic);
            addNewTask = findViewById(R.id.addNewTask);
            mic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    speechInput();
                }
            });
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
    }

    public void openMain2Activity() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    // Display an entire recordset to the screen.
    public void displayRecordSet() {
        List<Task_Table_Entity> tasks = DB.tasksDao().getTasks();

        // Update the list view
        adapter = new MyAdapter(tasks, this, this);
        recyclerView.setAdapter(adapter);
    }

    public void speechInput() {
        arrl.clear();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak normally into your phone");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try {
            startActivityForResult(intent, VOICE_RECOGNITION);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION) {
            Log.i("SpeechDemo", "## INFO 02: RequestCode VOICE_RECOGNITION = " + requestCode);
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for (int i = 0; i < results.size(); i++) {
                    final String result = results.get(i);
                    Log.i("SpeechDemo", "## INFO 05: Result: " + result);
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("message", result);
                    startActivity(intent);
                }
            }
        } else {
            Log.i("SpeechDemo", "## ERROR 01: Unexpected RequestCode = " + requestCode);
        }
    }

    @Override
    public void onItemClick(Task_Table_Entity task) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("task", task.getTask());
        intent.putExtra("dueDay", task.getDue_day());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(Task_Table_Entity task) {
        Toast.makeText(getApplicationContext(), "long pressed", Toast.LENGTH_LONG).show();
    }
}
