package com.example.benjamin.simpletodolist;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.speech.RecognizerIntent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    EditText task;
    TextView dEt, tEt;
    DatePickerDialog dPd;
    TimePickerDialog tPd;
    ImageButton clean1, clean2, saveButton, mic;
    Date convertedDate = null;

    ArrayList<String> arrl;

    public static int VOICE_RECOGNITION = 2;

    private int year, month, day, hour, minute;
    private boolean is24HourView;
    private String idFromMain = "";

    DatabaseReference databaseTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        arrl = new ArrayList<String>();

        task = findViewById(R.id.inputTask);
        mic = findViewById(R.id.imageButtonMic);
        dEt = findViewById(R.id.dateTextView);
        tEt = findViewById(R.id.timeTextView);

        clean1 = findViewById(R.id.blueClean1);
        clean2 = findViewById(R.id.blueClean2);
        saveButton = findViewById(R.id.checkMark);

        //getIntent(), data from main
        final Intent intent = getIntent();
        if(intent.getExtras() != null){
            //voice input
            String message = intent.getExtras().getString("message");
            idFromMain = intent.getExtras().getString("id");
            String taskFromMain = intent.getExtras().getString("task");
            String dueDayFromMain = intent.getExtras().getString("dueDay");
            if(message != ""){
                task.setText(message);
            }
            if(taskFromMain != ""){
                task.setText(taskFromMain);
            }
            if(dueDayFromMain != ""){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(dueDayFromMain);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }


        Calendar c = Calendar.getInstance();
        if(convertedDate != null){
            c.setTime(convertedDate);
        }
        if(dEt.getText().toString().equals("")){
            year = c.get(Calendar.YEAR);
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            String dayOfWeek = getDayOfweek(year, month, day);
            dEt.setText(dayOfWeek  + ", "+ getMonthInString(month) + " " + day + ", " + year);
        }

        if(tEt.getText().toString().equals("")){
            String apm, mMinute;
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            if(minute < 10){
                mMinute = "0" + minute;
            } else {
                mMinute = String.valueOf(minute);
            }

            if(c.get(Calendar.AM_PM) == Calendar.AM){
                apm = "am";
            } else {
                apm = "pm";
            }
            tEt.setText(hourToString(hour) + ": " + mMinute + " " + apm);

        }

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechInput();
            }
        });

        dEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dPd = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        String dayOfWeek = getDayOfweek(mYear, mMonth, mDay);
                        dEt.setText(dayOfWeek + ", " + getMonthInString(mMonth) + " " + mDay + ", " + mYear);
                        year = mYear;
                        day = mDay;
                        month = mMonth;
                    }
                }, year, month, day);
                dPd.show();
            }
        });

        tEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPd = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        String apm, mM;
                        Calendar time = Calendar.getInstance();
                        time.set(Calendar.HOUR_OF_DAY, mHour);
                        time.set(Calendar.MINUTE, mMinute);

                        hour = mHour;
                        minute = mMinute;
                        is24HourView = false;
                        if(time.get(Calendar.AM_PM) == Calendar.AM){
                            apm = "am";
                        } else {
                            apm = "pm";
                        }
                        if(mMinute < 10){
                            mM = "0" + String.valueOf(mMinute);
                        } else {
                            mM = String.valueOf(mMinute);
                        }
                        tEt.setText(hourToString(mHour) + ": " + mM + " " + apm);
                    }
                }, hour, minute, is24HourView);
                tPd.show();
            }
        });

        clean1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dEt.setText("");
            }
        });

        clean2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tEt.setText("");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = findViewById(R.id.inputTask);

                final String mTask = task.getText().toString();
                String mDueDay = dEt.getText().toString();
                String mDueTime = tEt.getText().toString();

                //check fields
                if(mTask.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please write down your task.",Toast.LENGTH_LONG).show();
                }else if(mDueDay.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please set a date for the due day.",Toast.LENGTH_LONG).show();
                }else if(mDueTime.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please set a time for the due day.",Toast.LENGTH_LONG).show();
                } else {
                    final String formattedDate;
                    final String mYear = String.valueOf(year);
                    String mMonth = String.valueOf(month+1);
                    String mDay = String.valueOf(day);
                    String mHour = String.valueOf(hour);
                    String mMinute = String.valueOf(minute);
                    final String mRepeat = "tast";
                    if((month+1) < 10){
                        mMonth = "0" + String.valueOf(month+1);
                    }
                    if(day < 10){
                        mDay = "0" + String.valueOf(day);
                    }
                    if(hour < 10){
                        mHour = "0" + String.valueOf(hour);
                    }
                    if(minute < 10){
                        mMinute = "0" + String.valueOf(minute);
                    }
                    formattedDate = mYear + "-" + mMonth + "-" + mDay + " " + mHour + ":" + mMinute;

                    if (idFromMain.isEmpty()) {
                        String id = databaseTasks.push().getKey();
                        Task task = new Task(id, mTask, formattedDate);
                        databaseTasks.child(id).setValue(task);
                        Toast.makeText(getApplicationContext(), "Task created", Toast.LENGTH_LONG).show();
                    } else {
                        DatabaseReference ref = FirebaseDatabase.getInstance()
                                .getReference()
                                .child("tasks")
                                .child(idFromMain);
                        ref.child("task").setValue(mTask);
                        ref.child("due_day").setValue(formattedDate);

                        Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION) {
            task = findViewById(R.id.inputTask);
            Log.i("SpeechDemo", "## INFO 02: RequestCode VOICE_RECOGNITION = " + requestCode);
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for (int i = 0; i < results.size(); i++) {
                    final String result = results.get(i);
                    Log.i("SpeechDemo", "## INFO 05: Result: " + result );
                    task.setText(result);
                }
            }
        } else {
            Log.i("SpeechDemo", "## ERROR 01: Unexpected RequestCode = " + requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String getMonthInString(int month){
        String mMonth;
        switch (month){
            case 0:
                mMonth = "Jan";
                break;
            case 1:
                mMonth = "Feb";
                break;
            case 2:
                mMonth = "Mar";
                break;
            case 3:
                mMonth = "Apr";
                break;
            case 4:
                mMonth = "May";
                break;
            case 5:
                mMonth = "Jun";
                break;
            case 6:
                mMonth = "Jul";
                break;
            case 7:
                mMonth = "Aug";
                break;
            case 8:
                mMonth = "Sep";
                break;
            case 9:
                mMonth = "Oct";
                break;
            case 10:
                mMonth = "Nov";
                break;
            case 11:
                mMonth = "Dec";
                break;
                default:
                    mMonth = "";

        }
        return mMonth;
    }

    private String getDayOfweek(int mYear, int mMonth, int mDay){
        SimpleDateFormat sDf = new SimpleDateFormat("EEE");
        Date date = new Date(mYear, mMonth, mDay-1);
        return sDf.format(date);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void speechInput(){
        arrl.clear();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak normally into your phone");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try{
            startActivityForResult(intent, VOICE_RECOGNITION);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String hourToString(int hour){
        String mH = String.valueOf(hour);
        if(hour < 10){
            mH = "0" + hour;
        }
        if(hour > 12 && hour < 22){
            mH = "0" + (hour-12);
        }
        if(hour > 21){
            mH = String.valueOf(hour-12);
        }
        return mH;
    }
}
