package com.example.benjamin.simpletodolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    EditText task;
    TextView dEt, tEt;
    DatePickerDialog dPd;
    TimePickerDialog tPd;
    ImageButton clean1, clean2, saveButton;

    private int year, month, day, hour, minute;
    private boolean is24HourView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
            String apm;
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            if(c.get(Calendar.AM_PM) == Calendar.AM){
                apm = "am";
            } else {
                apm = "pm";
            }
            tEt.setText(hour + ": " + minute + " " + apm);
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
                        String apm;
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
                        tEt.setText(mHour + ": " + mMinute + " " + apm);
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
                //check fields
                if(task.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please write down your task.",Toast.LENGTH_LONG).show();
                }else if(dEt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please set a date for the due day.",Toast.LENGTH_LONG).show();
                }else if(tEt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please set a time for the due day.",Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });
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

}
