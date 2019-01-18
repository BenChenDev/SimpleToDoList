package com.example.benjamin.simpletodolist;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    TextView dEt, tEt;
    DatePickerDialog dPd;

    private int year, month, day, weekDay;

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

        Calendar c = Calendar.getInstance();
        if(dEt.getText().toString().equals("")){
            year = c.get(Calendar.YEAR);
            day = c.get(Calendar.DAY_OF_MONTH);
            weekDay = c.get(Calendar.DAY_OF_WEEK);
            month = c.get(Calendar.MONTH);
            SimpleDateFormat sDf = new SimpleDateFormat("EEE");
            Date date = new Date(year, month, day-1);
            String dayOfWeek = sDf.format(date);
            dEt.setText(dayOfWeek + ", " + getMonthInString(month) + " " + day + ", " + year);
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

    private String getWeekDay (int weekDay){
        String mWeekDay;
        switch (weekDay){
            case 0:
                mWeekDay = "Sat";
                break;
            case 1:
                mWeekDay = "Sun";
                break;
            case 2:
                mWeekDay = "Mon";
                break;
            case 3:
                mWeekDay = "Tue";
                break;
            case 4:
                mWeekDay = "Web";
                break;
            case 5:
                mWeekDay = "Thu";
                break;
            case 6:
                mWeekDay = "Fri";
                break;
            default:
                mWeekDay = "";
                break;
        }
        return mWeekDay;
    }
}
