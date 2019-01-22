package com.example.benjamin.simpletodolist;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    EditText task;
    TextView dEt, tEt;
    DatePickerDialog dPd;
    TimePickerDialog tPd;
    ImageButton clean1, clean2, saveButton;

    DBAdapter myDb;

    ArrayList<String> arrl;

    public static int TTS_DATA_CHECK = 1;
    public static int VOICE_RECOGNITION = 2;

    private int year, month, day, hour, minute;
    private boolean is24HourView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openDB();

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

        dEt = findViewById(R.id.dateTextView);
        tEt = findViewById(R.id.timeTextView);

        clean1 = findViewById(R.id.blueClean1);
        clean2 = findViewById(R.id.blueClean2);
        saveButton = findViewById(R.id.checkMark);

        Calendar c = Calendar.getInstance();
        if(dEt.getText().toString().equals("")){
            year = c.get(Calendar.YEAR);
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);

            SimpleDateFormat sDf = new SimpleDateFormat("EEE");
            Date date = new Date(year, month, day-1);
            String dayOfWeek = sDf.format(date);
            dEt.setText(dayOfWeek + ", " + getMonthInString(month) + " " + day + ", " + year);
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

            if(hour > 12){
                tEt.setText((hour-12) + ": " + mMinute + " " + apm);
            } else {
                tEt.setText(hour + ": " + mMinute + " " + apm);
            }

        }

        dEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dPd = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        SimpleDateFormat sDf = new SimpleDateFormat("EEE");
                        Date date = new Date(mYear, mMonth, mDay-1);
                        String dayOfWeek = sDf.format(date);
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
                        if(mHour > 12){
                            mHour = mHour - 12;
                        }
                        if(mMinute < 10){
                            mM = "0" + String.valueOf(mMinute);
                        } else {
                            mM = String.valueOf(mMinute);
                        }
                        tEt.setText(mHour + ": " + mM + " " + apm);
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

                String mTask = task.getText().toString();
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
                    String formattedDate, mYear = String.valueOf(year), mMonth = String.valueOf(month+1), mDay = String.valueOf(day), mHour = String.valueOf(hour),
                            mMinute = String.valueOf(minute), mRepeat = "tast";
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
                    Long id  = myDb.insertRow(mTask, formattedDate, mRepeat);
                    Toast.makeText(getApplicationContext(), "Command Sent! .. ID = " + id, Toast.LENGTH_LONG).show();
                    Log.d("record created. ID= ", id.toString());
                    finish();
                    Intent openMainActivity= new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(openMainActivity);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String task = cursor.getString(DBAdapter.COL_TASK);
                String dueDate = cursor.getString(DBAdapter.COL_DUEDAY);
                String repeat = cursor.getString(DBAdapter.COL_REPEAT);

                // Append data to the message:
                message += "id=" + id
                        +", task =" + task
                        +", due date =" + dueDate
                        +", repeat =" + repeat
                        +"\n";

                // [TO_DO_B6]
                // Create arraylist(s)? and use it(them) in the list view
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();


        // [TO_DO_B7]
        // Update the list view

        // [TO_DO_B8]
        // Display a Toast message
        Log.d("Test", message);
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

    public void speechInput(){
        arrl.clear();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak normally into your phone");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try{
            startActivityForResult(intent, VOICE_RECOGNITION);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
