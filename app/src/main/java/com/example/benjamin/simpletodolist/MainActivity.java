package com.example.benjamin.simpletodolist;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener{
    private ImageButton addTaskButton, mic;
    private TextView addNewTask;
    private ArrayList<String> arrl;
    private List<Task> tasks;
    public static int VOICE_RECOGNITION = 2;
    //recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    DatabaseReference databaseTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasks = new ArrayList<>();

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for(DataSnapshot taskSnapshot : dataSnapshot.getChildren()){
                    Task task = taskSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if(tasks.size() > 0){
            setContentView(R.layout.main_activity_layout_2);
            //recyclerview
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this) );
            displayRecordSet();
//        } else {
//            setContentView(R.layout.activity_main);
//            tasks = new ArrayList<Task>();
//            mic = findViewById(R.id.mic);
//            addNewTask = findViewById(R.id.addNewTask);
//            mic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    speechInput();
//                }
//            });
//            addNewTask.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    openMain2Activity();
//                }
//            });
//        }

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
    public void onItemClick(Task task) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("id", task.getId());
        intent.putExtra("task", task.getTask());
        intent.putExtra("dueDay", task.getDue_day());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(final Task currentTask) {
        String mTask = currentTask.getTask();
        final String id = currentTask.getId();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setMessage("Do you want to delete " + mTask + "?").setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query query = ref.child("tasks").orderByChild("id").equalTo(id);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                displayRecordSet();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setTitle("Delete task");
        alert.show();
    }
}
